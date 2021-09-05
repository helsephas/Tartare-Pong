package models

import java.math.BigDecimal

class Team(
    var id: BigDecimal = BigDecimal(0),
    var name: String = "",
    var number: Int,
    var players: MutableList<Player> = arrayListOf(),
    var drinks: MutableList<Drink> = arrayListOf()
) {

    fun addPlayer(player: Player) {
        this.players.add(player)
    }

    fun initDrinks() {
        this.drinks.add(Drink(BigDecimal(1), 1))
        this.drinks.add(Drink(BigDecimal(2), 2))
        this.drinks.add(Drink(BigDecimal(3), 3))
        this.drinks.add(Drink(BigDecimal(4), 4))
        this.drinks.add(Drink(BigDecimal(5), 5))
        this.drinks.add(Drink(BigDecimal(6), 6))
    }

    fun getOtherPlayer(playerNumber: Int): Player {
        if (!getPlayerByNumber(playerNumber).trickShotAvailable) {
            for (player in this.players) {
                if (player.number != playerNumber) {
                    return player
                }
            }
            throw Exception("player not found")
        } else {
            return getPlayerByNumber(playerNumber)
        }
    }

    fun getOtherPlayerOnly(playerNumber: Int): Player {
        for (player in this.players) {
            if (player.number != playerNumber) {
                return player
            }
        }
        throw Exception("player not found")
    }

    fun getPlayerByNumber(number: Int): Player {
        for (player in this.players) {
            if (player.number == number) {
                return player
            }
        }
        return players[number - 1]
    }

    fun getDrinkByNumber(number: Int): Drink {
        for (drink in this.drinks) {
            if (drink.number == number) {
                return drink
            }
        }
        throw Exception("drink not found")
    }

    fun allDrinksAreDone(): Boolean {
        var nbDrinksDone = 0
        for (drink in drinks) {
            if (!drink.isDone) {
                nbDrinksDone += 1
            }
        }
        return nbDrinksDone == 6
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Team

        if (id != other.id) return false

        return true
    }

    override fun toString(): String {
        return "Team(id=$id, name='$name', number=$number, players=$players, drinks=$drinks)\b"
    }


}