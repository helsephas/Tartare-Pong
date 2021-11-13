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
    var hasFailedDefense:Boolean = false
    var hasDoubleShot:Boolean = false

    private var nbShotSucced: Int = 0
    private var drinkNumberScored: Int? = null
    var nbShots: Int = 2
    private var drinksDueToSameDrink: MutableList<Int>? = null

    private val NUMBER_DRINKS_WHEN_DOUBLE = 2

    private var firstScoredPlayer: Player? = null

    //var hasDoneTrickShot: Boolean = false

    private var turnNumber = 1

    fun startGame(): Match {
        addTeams()
        currentPlayerPlaying = this.getPlayerByTeamAndPlayerNumber(1, 1);
        return this
    }

    private fun isSuccess(): Boolean {
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
        currentPlayerPlaying.hasPlayed = true
        if (currentPlayerPlaying.trickShotAvailable) {
            currentPlayerPlaying.trickShotAvailable = false
        }
        changePlayer()
        if (isSuccess()) {
            if(currentDrinkSelected!!.isDone){
                hasDoubleShot = true
                nbShots = 2
                currentPlayerPlaying = getCurrentTeam().getPlayerByNumber(1)
            } else {
                currentDrinkSelected!!.isDone = true
            }
            getCurrentTeam().redemptionCount -= this.getLastShot().shotImpact
        }
        setCurrentShotTypeAccordingToCurrentPlayer()
        println(this.toString())
    }

    fun isDrinkSelected(): Boolean {
        return currentDrinkSelected != null
    }

    fun isDrinkNumberSelected(drinkNumber: Int): Boolean {
        return isDrinkSelected() && currentDrinkSelected!!.number == drinkNumber
    }

    private fun hasOnePlayerScored(): Boolean {
        return firstScoredPlayer != null
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

    private fun isDrinkCallable(numberDrinker: Int): Boolean {
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
        return getTeamByNumber(teamNumber).getPlayerByNumber(playerNumber)
    }

    fun changePlayerCurrentPlayerTo(playerNumber: Int) {
        currentPlayerPlaying = getCurrentTeam().getPlayerByNumber(playerNumber)
        setCurrentShotTypeAccordingToCurrentPlayer()
    }

    fun changeDefenderPlayerTo(playerNumber: Int){
        defenderPlayer = getOtherTeam().getPlayerByNumber(playerNumber)
    }

    fun disabledDefender(){
        defenderPlayer = null
    }

    private fun setCurrentShotTypeAccordingToCurrentPlayer() {
        currentShotType = if (currentPlayerPlaying.trickShotAvailable) {
            ShotType.TRICK_SHOT
        } else {
            ShotType.SIMPLE
        }
    }

    fun hasNotFailedAnymore(){
        this.hasFailedDefense = false
    }

    fun changeTeam() {
        if(nbShots == 0) {
            getCurrentTeam().resetAllPlayers()
            val otherTeam: Team = getOtherTeam()
            currentPlayerPlaying = otherTeam.getPlayerByNumber(1)
            nbShots = 2
            firstScoredPlayer = null
            turnNumber += 1
        }
    }

    fun getCurrentTeam(): Team {
        return getTeamByNumber(currentPlayerPlaying.teamNumber)
    }

    fun getOtherTeam(): Team {
        for (team in this.teams) {
            if (team.id != getCurrentTeam().id) {
                return team
            }
        }
        throw Exception("No other team found")
    }

    private fun changePlayer() {
        if ((currentShotType != ShotType.TRICK_SHOT && currentShotType != ShotType.AIR_SHOT) && !isSuccess()) {
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
        currentPlayerPlaying = getCurrentTeam().getOtherPlayer(currentPlayerPlaying.number)
    }


    fun getDrink(drinkNumber: Int): Drink {
        return getOtherTeam().getDrinkByNumber(drinkNumber)
    }

    private fun addTeams() {
        val team = Team(BigDecimal(1), "Team A", 1)
        team.init("Player 1","Player 2")
        this.teams.add(team)

        val team2 = Team(BigDecimal(2), "Team B", 2)
        team2.init("Player 3","Player 4")
        this.teams.add(team2)
    }

    fun hasDefender():Boolean{
        return defenderAvailable() && defenderPlayer != null
    }

    fun defenderAvailable():Boolean{
        return currentShotType.defendable()
    }

    fun defenderPlayer(): Player? {
        return this.defenderPlayer
    }



    fun getOtherPlayer(): Player {
        return getTeamByNumber(currentPlayerPlaying.teamNumber).getOtherPlayerOnly(currentPlayerPlaying.number)
    }

    fun isDrinkScoredDefinitivelyScoredForCurrentTeam(drinkNumber: Int): Boolean{
        return isDrinkScoredDefinitivelyScored(drinkNumber,getCurrentTeam().number)
    }

    fun isDrinkScoredDefinitivelyScoredForOtherTeam(drinkNumber: Int): Boolean{
        return isDrinkScoredDefinitivelyScored(drinkNumber,getOtherTeam().number)
    }

    private fun isDrinkScoredDefinitivelyScored(drinkNumber: Int, teamNumber: Int): Boolean {
        return !this.shots
            .filter { shot ->
                shot.isSuccess && shot.shotType.canBeSucced() &&
                        shot.turnNumber != turnNumber
                        && shot.shotedBy.teamNumber == teamNumber &&
                        getDrinkForShot(shot).number == drinkNumber
            }.toList().isEmpty()
    }





    private fun playerNumberTrickShoter(): Int {
        for (team in this.teams) {
            for (player in team.players) {
                if (player.trickShotAvailable) {
                    return player.number
                }
            }
        }
        return -1
    }



    fun isDrinkScored(drinkNumber: Int): Boolean {
        val shotsForCurrentTeam: ArrayList<Shot> = this.shots
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

    private fun getDrinkForShot(shot: Shot): Drink {
        when (shot) {
            is BounceShot -> return shot.drink!!
            is CallShot -> return shot.drink!!
            is SimpleShot -> return shot.drink!!
            is TrickShot -> return shot.drink!!
        }
        throw Exception("Impossible shot succed without drink")
    }



    fun containsValidsChoice(): Boolean {
        return true
    }

    override fun toString(): String {
        return "Match(teams=$teams, shots=$shots, currentPlayerPlaying=$currentPlayerPlaying, currentDrinkSelected=$currentDrinkSelected, currentShotType=$currentShotType, defenderPlayer=$defenderPlayer, nbShotSucced=$nbShotSucced, drinkNumberScored=$drinkNumberScored, nbShots=$nbShots, firstScoredPlayer=$firstScoredPlayer, turnNumber=$turnNumber)"
    }

    fun getPlayerScored(): Player? {
        return firstScoredPlayer
    }

    fun hasPlayerTrickShot(): Boolean {
        return playerNumberTrickShoter() != -1
    }

    fun hasOneShot(): Boolean {
        return nbShots == 1
    }

    private fun isLastShotSuccess(): Boolean {
        if (hasShot()) {
            return getLastShot().isSuccess
        }
        return false
    }

    fun hasShot():Boolean{
        return this.shots.isNotEmpty()
    }

    private fun getLastShot(): Shot {
        return this.shots.last()
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


}