package ui

import models.Drink
import models.Match
import models.Player

class FieldSide(
    var teamNumber: Int, var playersButton: MutableList<PlayerButton> = arrayListOf(),
    var drinksButton: MutableList<DrinkButton> = arrayListOf()

) {

    fun checkOtherPlayersState(match: Match) {
        this.playersButton.forEach { playerButton ->
            playerButton.checkOtherTeamCondition(match)
        }
    }

    fun desactivateDrinks(match: Match) {
        this.drinksButton.forEach { drinkButton ->
            drinkButton.checkDisableCondition(match)
        }
    }

    fun checkPlayersState(match: Match) {
        this.playersButton.forEach { playerButton ->
            playerButton.checkButtonConditions(match)
        }
    }

    fun checkDrinksState(match: Match) {
        this.drinksButton.forEach { drinkButton ->
            drinkButton.checkButtonCondition(match)
        }
    }
}