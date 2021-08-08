package models

import models.shots.*
import java.math.BigDecimal

class Match(
    var teams: MutableList<Team> = arrayListOf(),
    var shots: MutableList<Shot> = arrayListOf()
) {

    lateinit var currentPlayerPlaying: Player
    var currentDrinkSelected: Drink? = null
    var currentShopType: ShotType = ShotType.SIMPLE
    var defenderPlayer: Player? = null

    var nbShotSucced: Int = 0
    var drinkNumberScored: Int? = null
    var nbShots: Int = 2
    var drinksDueToSameDrink: MutableList<Int>? = null

    val NUMBER_DRINKS_WHEN_DOUBLE = 2

    fun startGame(): Match {
        addTeams()
        currentPlayerPlaying = this.getPlayerByTeamAndPlayerNumber(1, 1);
        return this
    }

    fun addShot(isSuccess: Boolean) {
        when (currentShopType) {
            ShotType.SIMPLE -> this.shots.add(SimpleShot(currentPlayerPlaying, isSuccess, currentDrinkSelected!!))
            ShotType.CALL -> this.shots.add(CallShot(currentPlayerPlaying, isSuccess, currentDrinkSelected!!))
            ShotType.TRICK_SHOT -> this.shots.add(TrickShot(currentPlayerPlaying, isSuccess, currentDrinkSelected!!))
            ShotType.AIR_SHOT -> {
                var shot = AirShot(currentPlayerPlaying, currentDrinkSelected!!)
                if (defenderPlayer != null) {
                    shot.defender = defenderPlayer!!
                }
                this.shots.add(shot)
            }
            ShotType.BOUNCE -> {
                var shot = BounceShot(currentPlayerPlaying, isSuccess, currentDrinkSelected!!)
                if (defenderPlayer != null) {
                    shot.defender = defenderPlayer!!
                }
                this.shots.add(shot)
            }
        }
        currentDrinkSelected!!.isDone = true
    }


    private fun getTeamByNumber(number: Int): Team {
        return teams[number - 1]
    }

    private fun getPlayerByTeamAndPlayerNumber(teamNumber: Int, playerNumber: Int): Player {
        return getTeamByNumber(teamNumber).getPlayerByNumber(playerNumber);
    }

    fun changePlayerCurrentPlayerTo(teamNumber: Int, playerNumber: Int) {
        currentPlayerPlaying = getPlayerByTeamAndPlayerNumber(teamNumber, playerNumber)
    }

    fun changeTeam(): Team {
        for (team in this.teams) {
            if (team.id != getTeamByNumber(currentPlayerPlaying.teamNumber).id) {
                return team
            }
        }
        throw Exception("Team not found")
    }

    fun changePlayer(): Player {
        return getTeamByNumber(currentPlayerPlaying.teamNumber).getOtherPlayer(currentPlayerPlaying.id)
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

    fun hasOneShot(): Boolean {
        return nbShots == 1
    }


}