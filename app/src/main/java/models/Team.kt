package models

import java.math.BigDecimal

class Team(var id: BigDecimal = BigDecimal(0),
           var name: String = "",
           var players: MutableList<Player> = arrayListOf(),
           var drinks: MutableList<Drink> = arrayListOf()) {

    fun addPlayer(player : Player){
        this.players.add(player)
    }

    fun initDrinks(){
        this.drinks.add(Drink(BigDecimal(1),1))
        this.drinks.add(Drink(BigDecimal(2),2))
        this.drinks.add(Drink(BigDecimal(3),3))
        this.drinks.add(Drink(BigDecimal(4),4))
        this.drinks.add(Drink(BigDecimal(5),5))
        this.drinks.add(Drink(BigDecimal(6),6))
    }
}