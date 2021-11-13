package models

import java.math.BigDecimal

class Team(
    var id: BigDecimal = BigDecimal(0),
    var name: String = "",
    var number: Int,
    var players: MutableList<Player> = arrayListOf(),
    var drinks: MutableList<Drink> = arrayListOf()
) {

    var redemptionCount:Int = 6

    fun init(player1Name:String,player2Name:String){
        this.addPlayer(Player(BigDecimal(1), player1Name, 1, this.number))
        this.addPlayer(Player(BigDecimal(2), player2Name, 2, this.number))
        this.initDrinks()
    }

    fun addPlayer(player: Player) {
        this.players.add(player)
    }


    fun initDrinks() {
        this.drinks.add(Drink(BigDecimal(1), 1,number))
        this.drinks.add(Drink(BigDecimal(2), 2,number))
        this.drinks.add(Drink(BigDecimal(3), 3,number))
        this.drinks.add(Drink(BigDecimal(4), 4,number))
        this.drinks.add(Drink(BigDecimal(5), 5,number))
        this.drinks.add(Drink(BigDecimal(6), 6,number))
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

    fun resetAllPlayers(){
        for(player in this.players){
            player.resetTurn()
        }
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