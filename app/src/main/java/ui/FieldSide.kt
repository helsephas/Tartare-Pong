package ui

import models.Match

class FieldSide(
    var teamNumber: Int, var playersButton: MutableList<PlayerButton> = arrayListOf(),
    var drinksButton: MutableList<DrinkButton> = arrayListOf()

) {

    fun checkDefenserPlayersStates(match: Match) {
        this.playersButton.forEach { playerButton ->
            playerButton.checkDefenserPlayerState(match)
        }
    }

    fun checkAttackTeamDrinksState(match: Match) {
        this.drinksButton.forEach { drinkButton ->
            drinkButton.checkAttackTeamDrinkState(match)
        }
    }

    fun checkAttackTeamPlayerState(match: Match) {
        this.playersButton.forEach { playerButton ->
            playerButton.checkAttackTeamPlayerState(match)
        }
    }

    fun checkDefenseDrinksState(match: Match) {
        this.drinksButton.forEach { drinkButton ->
            drinkButton.checkDefenseDrinkState(match)
        }
    }
}