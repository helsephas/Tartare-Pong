package ui

import models.Drink
import models.Match
import models.Player

class FieldSide(
    var teamNumber: Int, var playersButton: MutableList<PlayerButton> = arrayListOf(),
    var drinksButton: MutableList<DrinkButton> = arrayListOf()

) {

    fun desactivatePlayers() {
        this.playersButton.forEach { playerButton ->
            playerButton.desactivateSelection()
        }
    }

    fun desactivateDrinks() {
        this.drinksButton.forEach { drinkButton ->
            drinkButton.disabled()
        }
    }

    fun checkPlayersState(currentPlayer: Player, othePlayer: Player, nbShots:Int) {
        this.playersButton.forEach { playerButton ->
            playerButton.checkButtonConditions(currentPlayer,othePlayer,nbShots)
        }
    }

    fun checkDrinksState(match: Match) {
        this.drinksButton.forEach { drinkButton ->
            drinkButton.checkButtonCondition(match)
        }
    }
}