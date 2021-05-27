package models

import java.lang.Exception
import java.math.BigDecimal

class Match(
    var teams: MutableList<Team> = arrayListOf(),
    var shots: MutableList<Shot> = arrayListOf()
) {

    fun startGame() {
        addTeams()
    }

    fun failedShot(playerId: BigDecimal, shotType: ShotType) {
        this.shots.add(Shot(getPlayer(playerId), shotType, false))
    }

    fun addShot(playerId: BigDecimal, shotType: ShotType, isSuccess: Boolean) {
        this.shots.add(Shot(getPlayer(playerId), shotType, isSuccess))
    }

    fun getPlayer(playerId: BigDecimal): Player {
        for (team in this.teams) {
            for (player in team.players) {
                if (player.id == playerId) {
                    return player;
                }
            }
        }
        throw Exception("Team not found");
    }

    fun addTeams() {
        var team = Team(BigDecimal(1), "Team A")
        team.addPlayer(Player(BigDecimal(1), "Player 1"))
        team.addPlayer(Player(BigDecimal(2), "Player 2"))
        team.initDrinks()
        this.teams.add(team)

        var team2 = Team(BigDecimal(2), "Team B")
        team.addPlayer(Player(BigDecimal(3), "Player 3"))
        team.addPlayer(Player(BigDecimal(4), "Player 4"))
        team.initDrinks()
        this.teams.add(team2)
    }
}