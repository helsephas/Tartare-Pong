package models

import models.shots.AirShot
import models.shots.BounceShot
import models.shots.CallShot
import models.shots.Shot
import models.shots.ShotImpact
import models.shots.ShotType
import models.shots.SimpleShot
import models.shots.TrickShot
import java.math.BigDecimal

class Match(
    var teams: MutableList<Team> = arrayListOf(),
    var shots: MutableList<Shot> = arrayListOf()
) {

    lateinit var currentPlayerPlaying: Player
    var currentDrinkSelected: Drink? = null
    var currentShotType: ShotType = ShotType.SIMPLE

    //var secondShotType: ShotType? = null
    private var defenderPlayer: Player? = null
    var hasFailedDefense: Boolean = false
    var shotImpact: ShotImpact? = null
    private var firstScoredPlayer: Player? = null
    var turnNumber: Int = 1
    private var hasImpactToTreat: Boolean = false

    fun startGame(): Match {
        addTeams()
        currentPlayerPlaying = this.getPlayerByTeamAndPlayerNumber(1, 1);
        return this
    }

    //region shot
    fun addShot() {
        if (!hasImpactToTreat) {
            addShotToMatch()
            hasImpactToTreat = checkForShotImpact()
            if (!hasImpactToTreat) {
                changeCurrentPlayerState()
                if (getAttackTeam().allPlayersHasPlayed() && !shotImpact!!.hasToReplay) {
                    changeTeam()
                } else {
                    changePlayer()
                    setCurrentShotTypeAccordingToCurrentPlayer()
                }
            }
        }
    }

    private fun addShotToMatch() {
        when (currentShotType) {
            ShotType.SIMPLE -> this.shots.add(SimpleShot(currentPlayerPlaying, currentDrinkSelected, turnNumber))
            ShotType.CALL -> this.shots.add(CallShot(currentPlayerPlaying, currentDrinkSelected, turnNumber))
            ShotType.TRICK_SHOT -> {
                this.shots.add(TrickShot(currentPlayerPlaying, currentDrinkSelected, turnNumber))
            }

            ShotType.AIR_SHOT -> {
                this.shots.add(AirShot(currentPlayerPlaying, defenderPlayer, turnNumber))
            }

            ShotType.BOUNCE -> {
                this.shots.add(BounceShot(currentPlayerPlaying, currentDrinkSelected, defenderPlayer, turnNumber, hasFailedDefense))
            }
        }
    }


    private fun checkForShotImpact(): Boolean {
        if (hasOtherShotThisTurn()) {
            shotImpact = ShotImpact(this)
            return shotImpact!!.impactShot()
        }
        return false
    }

    fun hasImpactedShotToTreat(teamNumber: Int): Boolean {
        return getTeamByNumber(teamNumber).drinksTeamImpacted.size > 0
    }

    fun hasImpactedShotToTreat(): Boolean {
        return getAttackTeam().drinksTeamImpacted.size > 0 || getDefenseTeam().drinksTeamImpacted.size > 0
    }

    fun getOtherShotOfThisTurn(): Shot {
        return this.shots.first { shot -> shot.turnNumber == this.turnNumber }
    }

    private fun hasOtherShotThisTurn(): Boolean {
        return this.shots.count { shot -> shot.turnNumber == this.turnNumber && !shot.shotType.isTrickShot() } == 2
    }

    fun getLastShot(): Shot {
        return this.shots.last()
    }

    private fun setCurrentShotTypeAccordingToCurrentPlayer() {
        currentShotType = if (currentPlayerPlaying.trickShotAvailable) {
            ShotType.TRICK_SHOT
        } else {
            ShotType.SIMPLE
        }
    }

    fun changeShotType(shotType: ShotType) {
        if (shotType == ShotType.AIR_SHOT) {
            removeDrink()
        }
        disabledDefender()
        currentShotType = shotType
    }

    fun currentShotTypeIs(shotType: ShotType): Boolean {
        return currentShotType == shotType;
    }

    fun isShotTypeAvailable(shotType: ShotType): Boolean {
        return !hasCurrentPlayerHasTrickShot() && shotType != ShotType.TRICK_SHOT && shotType != ShotType.CALL || (currentDrinkIsCallable() && shotType == ShotType.CALL)
    }

    fun isTwoShotsOnSameDrinksScored(): Boolean {
        val lastShot: Shot = getLastShot()
        val otherShot: Shot = getOtherShotOfThisTurn()
        return lastShot.isSuccess && otherShot.isSuccess && getLastShotDrinkNumber() == getOtherShotDrinkNumber()
    }

    private fun getLastShotDrinkNumber(): Int {
        val lastShot: Shot = getLastShot()
        return determineDrinkNumberForShot(lastShot)
    }

    private fun getOtherShotDrinkNumber(): Int {
        val otherShot: Shot = getOtherShotOfThisTurn()
        return determineDrinkNumberForShot(otherShot)
    }

    private fun determineDrinkNumberForShot(shot: Shot): Int {
        when (shot.shotType) {
            ShotType.SIMPLE -> {
                val simpleShot = shot as SimpleShot
                return simpleShot.drink!!.number
            }

            ShotType.BOUNCE -> {
                val bounceShot = shot as BounceShot
                return bounceShot.drink!!.number
            }

            ShotType.CALL -> {
                val callShot = shot as CallShot
                return callShot.drink!!.number
            }

            ShotType.AIR_SHOT -> {
                throw RuntimeException("Impossible case")
            }

            ShotType.TRICK_SHOT -> {
                val trickShot = shot as TrickShot
                return trickShot.drink!!.number
            }
        }
    }

    //endregion


    //region player

    private fun hasOtherPlayerAlreadyScoredThisTurn(): Boolean {
        return !this.shots.none { shot -> shot.turnNumber == turnNumber && shot.shotedBy.number != currentPlayerPlaying.number }
    }

    fun has2PlayersAlreadyPlayed(): Boolean {
        return hasCurrentPlayerHasTrickShot() && hasOtherPlayerAlreadyScoredThisTurn()
    }

    fun currentPlayerHasAlreadyPlayed(): Boolean {
        return hasOnePlayerScored() && currentPlayerPlaying.number == firstScoredPlayer!!.number
    }

    private fun hasOnePlayerScored(): Boolean {
        return firstScoredPlayer != null
    }

    fun hasCurrentPlayerHasTrickShot(): Boolean {
        return currentPlayerPlaying.trickShotAvailable
    }

    fun changePlayerCurrentPlayerTo(playerNumber: Int) {
        currentPlayerPlaying = getAttackTeam().getPlayerByNumber(playerNumber)
        setCurrentShotTypeAccordingToCurrentPlayer()
    }

    fun isCurrentPlayerDefender(teamNumber: Int, playerNumber: Int): Boolean {
        return hasDefender() && teamNumber == getDefenseTeam().number && defenderPlayer()!!.number == playerNumber
    }

    fun isDefendableButNotDefended(teamNumber: Int): Boolean {
        return defenderAvailable() && teamNumber == getDefenseTeam().number
    }

    fun changeDefenderPlayerTo(playerNumber: Int) {
        defenderPlayer = getDefenseTeam().getPlayerByNumber(playerNumber)
    }

    fun disabledDefender() {
        hasFailedDefense = false
        defenderPlayer = null
    }

    fun hasDefender(): Boolean {
        return defenderAvailable() && defenderPlayer != null
    }

    fun isPlayerCurrentPlayer(playerNumber: Int): Boolean {
        return currentPlayerPlaying.number == playerNumber
    }

    fun hasOtherPlayerTrickshotAvailable(playerNumber: Int): Boolean {
        return try {
            val otherPlayer: Player = getOtherAttackant()
            playerNumber == otherPlayer.number && otherPlayer.trickShotAvailable

        } catch (e: NoSuchElementException) {
            false
        }
    }

    fun hasSecondPlayerNotAlreadyPlayed(playerNumber: Int): Boolean {
        return try {
            val otherPlayer: Player = getOtherAttackant()
            playerNumber == otherPlayer.number && !otherPlayer.hasPlayed
        } catch (e: NoSuchElementException) {
            false
        }
    }

    fun defenderAvailable(): Boolean {
        return currentShotType.defendable()
    }

    fun defenderSelectedForDefense(playerNumber: Int): Boolean {
        return hasDefender() && defenderPlayer()?.number == playerNumber
    }

    private fun defenderPlayer(): Player? {
        return this.defenderPlayer
    }

    fun revertDefenseFailed() {
        hasFailedDefense = !hasFailedDefense
    }

    fun changeTeam() {
        getAttackTeam().resetAllPlayers()
        val otherTeam: Team = getDefenseTeam()
        currentPlayerPlaying = otherTeam.getPlayerByNumber(1)
        setCurrentShotTypeAccordingToCurrentPlayer()
        firstScoredPlayer = null
        turnNumber += 1
    }

    fun isAttackTeam(teamNumber: Int): Boolean {
        return teamNumber == getAttackTeam().number
    }

    fun getAttackTeam(): Team {
        return getTeamByNumber(currentPlayerPlaying.teamNumber)
    }

    fun getDefenseTeam(): Team {
        for (team in this.teams) {
            if (team.id != getAttackTeam().id) {
                return team
            }
        }
        throw Exception("No other team found")
    }

    private fun getOtherAttackant(): Player {
        return try {
            getTeamByNumber(currentPlayerPlaying.teamNumber).getOtherPlayerOnly(currentPlayerPlaying.number)
        } catch (e: NoSuchElementException) {
            currentPlayerPlaying
        }
    }

    private fun changeCurrentPlayerState() {
        val lastShot: Shot = getLastShot()
        if (!lastShot.isSuccess && (lastShot.shotType != ShotType.TRICK_SHOT && lastShot.shotType != ShotType.AIR_SHOT)) {
            changeOtherAttackToPlayedState()
            currentPlayerPlaying.changeToTrickShot()
        } else {
            changeOtherAttackToPlayedState()
            currentPlayerPlaying.changeToPlayedState()
            if (lastShot.isSuccess) {
                firstScoredPlayer = currentPlayerPlaying
            }
        }
    }

    private fun changeOtherAttackToPlayedState() {
        if (getOtherAttackant().trickShotAvailable) {
            getOtherAttackant().changeToPlayedState()
        }
    }


    private fun getTeamByNumber(number: Int): Team {
        return teams[number - 1]
    }

    private fun getPlayerByTeamAndPlayerNumber(teamNumber: Int, playerNumber: Int): Player {
        return getTeamByNumber(teamNumber).getPlayerByNumber(playerNumber)
    }

    private fun changePlayer() {
        currentPlayerPlaying = getAttackTeam().getOtherPlayerOnly(currentPlayerPlaying.number)
    }


    private fun addTeams() {
        val team = Team(BigDecimal(1), "Team A", 1)
        team.init("Player 1", "Player 2")
        this.teams.add(team)

        val team2 = Team(BigDecimal(2), "Team B", 2)
        team2.init("Player 3", "Player 4")
        this.teams.add(team2)
    }

    //endregion
    //region drinks
    fun addDrink(drinkNumber: Int) {
        this.currentDrinkSelected = this.getDrink(drinkNumber)
    }

    fun removeDrink() {
        this.currentDrinkSelected = null
    }

    fun isDrinkSelected(): Boolean {
        return currentDrinkSelected != null
    }

    fun isDrinkNumberSelected(drinkNumber: Int): Boolean {
        return isDrinkSelected() && currentDrinkSelected!!.number == drinkNumber
    }

    fun currentDrinkIsCallable(): Boolean {
        return isDrinkSelected() && isDrinkCallable(currentDrinkSelected!!.number)
    }

    fun isDrinkTemporalyScored(drinkNumber: Int): Boolean {
        return isDrinkScored(drinkNumber) && !isDrinkNumberSelected(drinkNumber)
    }

    private fun isDrinkCallable(drinkNumber: Int): Boolean {
        if ((getDefenseTeam().nbDrinksNotDone() > 4) ||
            (drinkNumber == 3 || drinkNumber == 2 || drinkNumber == 5)
        ) {
            return false
        }
        return if (drinkNumber == 1
            && getDrink(2).isDone
            && getDrink(3).isDone
            && !getDrink(5).isDone
        ) {
            true
        } else if (drinkNumber == 4
            && getDrink(2).isDone
            && getDrink(5).isDone
            && !getDrink(3).isDone
        ) {
            true
        } else (drinkNumber == 6
                && getDrink(3).isDone
                && getDrink(5).isDone
                && !getDrink(2).isDone)
    }

    fun isDrinkScoredDefinitivelyScoredForCurrentTeam(drinkNumber: Int): Boolean {
        return isDrinkScoredDefinitivelyScored(drinkNumber, getAttackTeam().number)
    }

    fun isDrinkScoredScoredThisTurnForCurrentTeam(drinkNumber: Int): Boolean {
        return isDrinkScoredDefinitivelyScoredThisTurn(drinkNumber, getAttackTeam().number)
    }

    fun isDrinkScoredDefinitivelyScoredForOtherTeam(drinkNumber: Int): Boolean {
        return isDrinkScoredDefinitivelyScored(drinkNumber, getDefenseTeam().number)
    }

    fun isDrinkSelectedForImpact(teamNumber: Int, drinkNumber: Int): Boolean {
        return getTeamByNumber(teamNumber).drinksTeamImpacted.contains(drinkNumber)
    }

    fun selectDrinkForImpact(teamNumber: Int, drinkNumber: Int) {
        if (getTeamByNumber(teamNumber).drinksTeamImpacted.isNotEmpty()) {
            getTeamByNumber(teamNumber).drinksTeamImpacted.removeAt(getTeamByNumber(teamNumber).drinksTeamImpacted.lastIndex)
        }
        getTeamByNumber(teamNumber).drinksTeamImpacted.add(drinkNumber)
    }

    private fun isDrinkScoredDefinitivelyScored(drinkNumber: Int, teamNumber: Int): Boolean {
        return this.shots
            .filter { shot ->
                shot.isSuccess && shot.turnNumber != turnNumber
                        && shot.shotedBy.teamNumber == teamNumber &&
                        getDrinkForShot(shot).number == drinkNumber
            }.toList()
            .isNotEmpty()
    }

    private fun isDrinkScoredDefinitivelyScoredThisTurn(drinkNumber: Int, teamNumber: Int): Boolean {
        return this.shots
            .filter { shot ->
                shot.isSuccess && shot.turnNumber == turnNumber
                        && shot.shotedBy.teamNumber == teamNumber &&
                        getDrinkForShot(shot).number == drinkNumber
            }.toList()
            .isNotEmpty()
    }

    fun isDrinkScored(drinkNumber: Int): Boolean {
        val shotsForCurrentTeam: ArrayList<Shot> = this.shots
            .filter { shot ->
                shot.isSuccess
                        && shot.shotedBy.teamNumber == currentPlayerPlaying.teamNumber
                        && shot.turnNumber == turnNumber
            }
            .toCollection(arrayListOf())
        shotsForCurrentTeam.forEach { shot ->
            if (getDrinkForShot(shot).number == drinkNumber) {
                return true
            }
        }
        return false
    }

    private fun getDrinkForShot(shot: Shot): Drink {
        when (shot) {
            is BounceShot -> return shot.drink!!
            is CallShot -> return shot.drink!!
            is SimpleShot -> return shot.drink!!
            is TrickShot -> return shot.drink!!
        }
        throw Exception("Impossible shot succed without drink")
    }

    private fun getDrink(drinkNumber: Int): Drink {
        return getDefenseTeam().getDrinkByNumber(drinkNumber)
    }

    //endregion


    override fun toString(): String {
        //return "Match(teams=$teams, shots=$shots, currentPlayerPlaying=$currentPlayerPlaying, currentDrinkSelected=$currentDrinkSelected, currentShotType=$currentShotType, defenderPlayer=$defenderPlayer, nbShotSucced=$nbShotSucced, drinkNumberScored=$drinkNumberScored, nbShots=$nbShots, firstScoredPlayer=$firstScoredPlayer, turnNumber=$turnNumber)"
        return ""
    }


}