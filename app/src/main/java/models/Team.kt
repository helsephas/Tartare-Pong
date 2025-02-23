package models

import java.math.BigDecimal

class Team(
    var id: BigDecimal = BigDecimal(0),
    var name: String = "",
    var number: Int,
    var players: MutableList<Player> = arrayListOf(),
    var drinks: MutableList<Drink> = arrayListOf()
) {

    var redemptionCount: Int = 6
    var drinksTeamImpacted: MutableList<Int> = arrayListOf()

    fun init(player1Name: String, player2Name: String) {
        this.addPlayer(Player(BigDecimal(1), player1Name, 1, this.number))
        this.addPlayer(Player(BigDecimal(2), player2Name, 2, this.number))
        this.initDrinks()
    }

    private fun addPlayer(player: Player) {
        this.players.add(player)
    }


    private fun initDrinks() {
        this.drinks.add(Drink(BigDecimal(1), 1, number))
        this.drinks.add(Drink(BigDecimal(2), 2, number))
        this.drinks.add(Drink(BigDecimal(3), 3, number))
        this.drinks.add(Drink(BigDecimal(4), 4, number))
        this.drinks.add(Drink(BigDecimal(5), 5, number))
        this.drinks.add(Drink(BigDecimal(6), 6, number))
    }

    fun getOtherPlayerOnly(playerNumber: Int): Player {
        return this.players.first{ player -> player.number != playerNumber && !player.hasPlayed }
    }

    fun getPlayerByNumber(number: Int): Player {
        return this.players.first{ player -> player.number == number }
    }

    fun getDrinkByNumber(number: Int): Drink {
        return this.drinks.first { drink -> drink.number == number }
    }

    fun nbDrinksNotDone():Int{
        return drinks.filter { drink -> !drink.isDone  }.count()
    }

    fun resetAllPlayers() {
        players.forEach { player -> player.resetTurn() }
    }

    fun allPlayersHasPlayed(): Boolean {
        return players.filter { player -> player.hasPlayed }.size == 2
    }

    fun allDrinksAreDone(): Boolean {
        var nbDrinksDone = 0
        for (drink in drinks) {
            if (drink.isDone) {
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
        return "Team(id=$id, name='$name', number=$number, players=$players, drinks=$drinks)"
    }


}