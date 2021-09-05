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
    var defenderPlayer: Player? = null

    var nbShotSucced: Int = 0
    var drinkNumberScored: Int? = null
    var nbShots: Int = 2
    var drinksDueToSameDrink: MutableList<Int>? = null

    val NUMBER_DRINKS_WHEN_DOUBLE = 2

    var firstScoredPlayer: Player? = null

    //var hasDoneTrickShot: Boolean = false

    var turnNumber = 1

    fun startGame(): Match {
        addTeams()
        currentPlayerPlaying = this.getPlayerByTeamAndPlayerNumber(1, 1);
        return this
    }

    fun isSuccess(): Boolean {
        return isDrinkSelected()
    }

    fun addShot() {
        when (currentShotType) {
            ShotType.SIMPLE -> this.shots.add(SimpleShot(currentPlayerPlaying, isSuccess(), currentDrinkSelected, turnNumber))
            ShotType.CALL -> this.shots.add(CallShot(currentPlayerPlaying, isSuccess(), currentDrinkSelected, turnNumber))
            ShotType.TRICK_SHOT -> {
                this.shots.add(TrickShot(currentPlayerPlaying, isSuccess(), currentDrinkSelected, turnNumber))
            }
            ShotType.AIR_SHOT -> {
                this.shots.add(AirShot(currentPlayerPlaying, defenderPlayer, turnNumber))
            }
            ShotType.BOUNCE -> {
                this.shots.add(BounceShot(currentPlayerPlaying, isSuccess(), currentDrinkSelected, defenderPlayer, turnNumber))
            }
        }
        if (currentPlayerPlaying.trickShotAvailable) {
            currentPlayerPlaying.trickShotAvailable = false
        }
        changePlayer()
        if (isSuccess()) {
            currentDrinkSelected!!.isDone = true
        }
        if (nbShots == 0) {
            changeTeam()
        }
        setCurrentShotTypeAccordingToCurrentPlayer()
        System.out.println(this.toString())
    }

    fun isDrinkSelected(): Boolean {
        return currentDrinkSelected != null
    }

    fun isDrinkNumberSelected(drinkNumber: Int): Boolean {
        return isDrinkSelected() && currentDrinkSelected!!.number == drinkNumber
    }

    fun hasOnePlayerScored(): Boolean {
        return firstScoredPlayer != null
    }

    fun getPlayerScored(): Player? {
        return firstScoredPlayer
    }

    fun currentDrinkIsCallable(): Boolean {
        return isDrinkSelected() && isDrinkCallable(currentDrinkSelected!!.number)
    }

    fun currentPlayerHasAlreadyPlayed(): Boolean {
        return hasOnePlayerScored() && currentPlayerPlaying.number == firstScoredPlayer!!.number
    }

    fun hasCurrentPlayerHasTrickShot(): Boolean {
        return currentPlayerPlaying.trickShotAvailable
    }

    fun isDrinkCallable(numberDrinker: Int): Boolean {
        return (isCallable(numberDrinker, 2, 3) ||
                isCallable(numberDrinker, 5, 2) ||
                isCallable(numberDrinker, 5, 3))
    }

    private fun isCallable(drinkNumber: Int, firstDrinkNumberDone: Int, secondDrinkNumberDone: Int): Boolean {
        return (drinkNumber == 1 || drinkNumber == 4 || drinkNumber == 6) && getDrink(firstDrinkNumberDone).isDone && getDrink(secondDrinkNumberDone).isDone
    }

    private fun getTeamByNumber(number: Int): Team {
        return teams[number - 1]
    }

    private fun getPlayerByTeamAndPlayerNumber(teamNumber: Int, playerNumber: Int): Player {
        return getTeamByNumber(teamNumber).getPlayerByNumber(playerNumber);
    }

    fun changePlayerCurrentPlayerTo(teamNumber: Int, playerNumber: Int) {
        currentPlayerPlaying = getPlayerByTeamAndPlayerNumber(teamNumber, playerNumber)
        setCurrentShotTypeAccordingToCurrentPlayer()
    }

    fun setCurrentShotTypeAccordingToCurrentPlayer() {
        if (currentPlayerPlaying.trickShotAvailable) {
            currentShotType = ShotType.TRICK_SHOT
        } else {
            currentShotType = ShotType.SIMPLE
        }
    }

    fun changeTeam() {
        for (team in this.teams) {
            if (team.id != getTeamByNumber(currentPlayerPlaying.teamNumber).id) {
                currentPlayerPlaying = team.getPlayerByNumber(1)
                nbShots = 2
                firstScoredPlayer = null
                turnNumber += 1
            }
        }
    }

    private fun changePlayer() {
        if (currentShotType != ShotType.TRICK_SHOT && !isSuccess()) {
            currentPlayerPlaying.trickShotAvailable = true
        }
        if (currentShotType == ShotType.TRICK_SHOT && isSuccess()) {
            currentPlayerPlaying.trickShotAvailable = false
        }
        if (currentShotType != ShotType.TRICK_SHOT && isSuccess()) {
            firstScoredPlayer = currentPlayerPlaying
        }
        if (currentShotType != ShotType.TRICK_SHOT) {
            nbShots -= 1
        }
        currentPlayerPlaying = getTeamByNumber(currentPlayerPlaying.teamNumber).getOtherPlayer(currentPlayerPlaying.number)
    }


    fun getDrink(drinkNumber: Int): Drink {
        return getTeamByNumber(currentPlayerPlaying.teamNumber).getDrinkByNumber(drinkNumber)
    }

    private fun addTeams() {
        val team = Team(BigDecimal(1), "Team A", 1)
        team.addPlayer(Player(BigDecimal(1), "Player 1", 1, team.number))
        team.addPlayer(Player(BigDecimal(2), "Player 2", 2, team.number))
        team.initDrinks()
        this.teams.add(team)

        val team2 = Team(BigDecimal(2), "Team B", 2)
        team2.addPlayer(Player(BigDecimal(3), "Player 3", 1, team.number))
        team2.addPlayer(Player(BigDecimal(4), "Player 4", 2, team.number))
        team2.initDrinks()
        this.teams.add(team2)
    }

    fun needToRemoveDrinks(): Boolean {
        return drinksDueToSameDrink != null && drinksDueToSameDrink?.size != NUMBER_DRINKS_WHEN_DOUBLE;
    }

    fun hasDrinksToRemove(): Boolean {
        return drinksDueToSameDrink != null
    }

    fun changeDrinkSelected(drinkNumber: Int) {
        currentDrinkSelected = getDrink(drinkNumber)
    }

    fun nbShotAvailable(): Int {
        return nbShots
    }

    fun getOtherPlayer():Player{
        return getTeamByNumber(currentPlayerPlaying.teamNumber).getOtherPlayerOnly(currentPlayerPlaying.number)
    }

    fun isDrinkScoredDefinitivelyScored(drinkNumber: Int): Boolean {
        var shotsForCurrentTeam: ArrayList<Shot> = this.shots
            .filter { shot ->
                shot.isSuccess && shot.shotType.canBeSucced() &&
                        shot.shotedBy.teamNumber == currentPlayerPlaying.teamNumber &&
                        shot.turnNumber != turnNumber
            }
            .toCollection(arrayListOf())
        shotsForCurrentTeam.forEach { shot ->
            if (getDrinkForShot(shot).number == drinkNumber) {
                return true
            }
        }
        return false
    }

    fun hasPlayerTrickShot(): Boolean {
        return playerNumberTrickShoter() != -1
    }

    fun playerNumberTrickShoter(): Int {
        for (team in this.teams) {
            for (player in team.players) {
                if (player.trickShotAvailable) {
                    return player.number
                }
            }
        }
        return -1
    }

    fun isLastShotSuccess(): Boolean {
        if (!this.shots.isEmpty()) {
            return this.shots.last().isSuccess
        }
        return false
    }

    fun isDrinkScored(drinkNumber: Int): Boolean {
        var shotsForCurrentTeam: ArrayList<Shot> = this.shots
            .filter { shot ->
                shot.isSuccess &&
                        shot.shotType.canBeSucced() &&
                        shot.shotedBy.teamNumber == currentPlayerPlaying.teamNumber &&
                        shot.turnNumber == turnNumber
            }
            .toCollection(arrayListOf())
        shotsForCurrentTeam.forEach { shot ->
            if (getDrinkForShot(shot).number == drinkNumber) {
                return true
            }
        }
        return false
    }

    fun getDrinkForShot(shot: Shot): Drink {
        when (shot) {
            is BounceShot -> return shot.drink!!
            is CallShot -> return shot.drink!!
            is SimpleShot -> return shot.drink!!
            is TrickShot -> return shot.drink!!
        }
        throw Exception("Impossible shot succed without drink")
    }

    fun hasOneShot(): Boolean {
        return nbShots == 1
    }

    fun containsValidsChoice(): Boolean {
        return true
    }

    override fun toString(): String {
        return "Match(teams=$teams, shots=$shots, currentPlayerPlaying=$currentPlayerPlaying, currentDrinkSelected=$currentDrinkSelected, currentShotType=$currentShotType, defenderPlayer=$defenderPlayer, nbShotSucced=$nbShotSucced, drinkNumberScored=$drinkNumberScored, nbShots=$nbShots, firstScoredPlayer=$firstScoredPlayer, turnNumber=$turnNumber)"
    }


}