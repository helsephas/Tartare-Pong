package models

import models.shots.*
import java.math.BigDecimal

class Match(
    var teams: MutableList<Team> = arrayListOf(),
    var shots: MutableList<Shot> = arrayListOf()
) {

    lateinit var currentPlayerPlaying: Player
    var currentDrinkSelected: Drink? = null
    var currentShotType: ShotType = ShotType.SIMPLE
    var secondShotType: ShotType? = null
    private var defenderPlayer: Player? = null
    var hasFailedDefense: Boolean = false
    var hasDoubleShot: Boolean = false
    var shotImpact: ShotImpact? = null

    private var nbShotSucced: Int = 0
    private var drinkNumberScored: Int? = null
    var nbShots: Int = 2

    private var firstScoredPlayer: Player? = null

    var turnNumber = 1

    fun startGame(): Match {
        addTeams()
        currentPlayerPlaying = this.getPlayerByTeamAndPlayerNumber(1, 1);
        return this
    }

    //region shot
    fun addShot() {
        addShotToMatch()
        changeCurrentPlayerState()
        changePlayer()
        setCurrentShotTypeAccordingToCurrentPlayer()
        println(this.toString())
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
                this.shots.add(BounceShot(currentPlayerPlaying, currentDrinkSelected, defenderPlayer, turnNumber,hasFailedDefense))
            }
        }
    }


    private fun checkForShotImpact(): Boolean {
        var lastShot: Shot = getLastShot()
        shotImpact = ShotImpact(lastShot)
        return shotImpact!!.impactShot()
    }

    fun containsValidsChoice(): Boolean {
        return true
    }
    private fun getLastShot(): Shot {
        return this.shots.last()
    }

    private fun setCurrentShotTypeAccordingToCurrentPlayer() {
        currentShotType = if (currentPlayerPlaying.trickShotAvailable) {
            ShotType.TRICK_SHOT
        } else {
            ShotType.SIMPLE
        }
    }

    fun currentShotTypeIs(shotType: ShotType): Boolean {
        return currentShotType == shotType;
    }

    fun isShotTypeAvailable(shotType: ShotType): Boolean {
        return !hasCurrentPlayerHasTrickShot() && shotType != ShotType.TRICK_SHOT && shotType != ShotType.CALL || (currentDrinkIsCallable() && shotType == ShotType.CALL)
    }

    //endregion


    //region player
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
        defenderPlayer = null
    }

    fun hasDefender(): Boolean {
        return defenderAvailable() && defenderPlayer != null
    }

    fun isPlayerCurrentPlayer(playerNumber:Int):Boolean{
        return currentPlayerPlaying.number == playerNumber
    }

    fun hasOtherPlayerTrickshotAvailable(playerNumber:Int): Boolean {
        val otherPlayer: Player = getOtherAttackant()
        return playerNumber == otherPlayer.number && otherPlayer.trickShotAvailable
    }

    fun hasSecondPlayerNotAlreadyPlayed(playerNumber:Int): Boolean {
        val otherPlayer: Player = getOtherAttackant()
        return playerNumber == otherPlayer.number && !otherPlayer.hasPlayed
    }

    fun defenderAvailable(): Boolean {
        return currentShotType.defendable()
    }

    fun defenderSelectedForDefense(playerNumber:Int):Boolean{
        return hasDefender() && defenderPlayer()?.number == playerNumber
    }

    private fun defenderPlayer(): Player? {
        return this.defenderPlayer
    }

    fun revertDefenseFailed(){
        hasFailedDefense =  !hasFailedDefense
    }

    fun changeTeam() {
        if (nbShots == 0) {
            getAttackTeam().resetAllPlayers()
            val otherTeam: Team = getDefenseTeam()
            currentPlayerPlaying = otherTeam.getPlayerByNumber(1)
            nbShots = 2
            firstScoredPlayer = null
            turnNumber += 1
        }
    }

    fun isAttackTeam(teamNumber:Int):Boolean{
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
        return getTeamByNumber(currentPlayerPlaying.teamNumber).getOtherPlayerOnly(currentPlayerPlaying.number)
    }

    private fun changeCurrentPlayerState() {
        val lastShot:Shot = getLastShot()
        if(lastShot.isSuccess){
            if (lastShot.shotType != ShotType.TRICK_SHOT){
                firstScoredPlayer = currentPlayerPlaying
            }
            currentPlayerPlaying.changeToPlayedState()
        } else {
            if ((lastShot.shotType != ShotType.TRICK_SHOT && lastShot.shotType != ShotType.AIR_SHOT)) {
                currentPlayerPlaying.changeToTrickShot()
            }
        }
        if (lastShot.shotType != ShotType.TRICK_SHOT) {
            nbShots -= 1
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

    fun isDrinkTemporalyScored(drinkNumber: Int):Boolean{
        return isDrinkScored(drinkNumber) && !isDrinkNumberSelected(drinkNumber)
    }

    private fun isDrinkCallable(drinkNumber: Int): Boolean {
        if ((getDefenseTeam().nbDrinksNotDone() > 4) ||
                (drinkNumber == 3 || drinkNumber == 2 || drinkNumber == 5) ){
            return false
        }
        return if (drinkNumber == 1
            && getDrink(2).isDone
            && getDrink(3).isDone
            && !getDrink(5).isDone) {
            true
        } else if (drinkNumber == 4
            && getDrink(2).isDone
            && getDrink(5).isDone
            && !getDrink(3).isDone) {
            true
        } else (drinkNumber == 6
                && getDrink(3).isDone
                && getDrink(5).isDone
                && !getDrink(2).isDone)
    }

    fun isDrinkScoredDefinitivelyScoredForCurrentTeam(drinkNumber: Int): Boolean {
        return isDrinkScoredDefinitivelyScored(drinkNumber, getAttackTeam().number)
    }

    fun isDrinkScoredDefinitivelyScoredForOtherTeam(drinkNumber: Int): Boolean {
        return isDrinkScoredDefinitivelyScored(drinkNumber, getDefenseTeam().number)
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
        return "Match(teams=$teams, shots=$shots, currentPlayerPlaying=$currentPlayerPlaying, currentDrinkSelected=$currentDrinkSelected, currentShotType=$currentShotType, defenderPlayer=$defenderPlayer, nbShotSucced=$nbShotSucced, drinkNumberScored=$drinkNumberScored, nbShots=$nbShots, firstScoredPlayer=$firstScoredPlayer, turnNumber=$turnNumber)"
    }


}