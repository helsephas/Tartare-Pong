package ui

import models.Match

class Field(var fieldSides: MutableList<FieldSide> = arrayListOf(),var shotButtons:MutableList<ShotButton> = arrayListOf()) {

    lateinit var failedButton: FailedButton

    fun init(playerButtonTeamA: MutableList<PlayerButton>, playerButtonTeamB: MutableList<PlayerButton>,
            drinksButtonTeamA:MutableList<DrinkButton>, drinksButtonTeamB: MutableList<DrinkButton>,
             shotButtons:MutableList<ShotButton>, failedButton: FailedButton) {
        this.fieldSides.add(FieldSide(1,playerButtonTeamA,drinksButtonTeamA))
        this.fieldSides.add(FieldSide(2,playerButtonTeamB,drinksButtonTeamB))
        this.shotButtons = shotButtons
        this.failedButton = failedButton
    }

    fun checkFieldConfiguration(match: Match){
        if(match.currentPlayerPlaying.teamNumber == fieldSides[0].teamNumber){
            this.fieldSides[0].desactivateDrinks()
            this.fieldSides[0].checkPlayersState(match.currentPlayerPlaying,match.getOtherPlayer(),match.nbShots)
            this.fieldSides[1].desactivatePlayers()
            this.fieldSides[1].checkDrinksState(match)
        } else {
            this.fieldSides[1].desactivateDrinks()
            this.fieldSides[1].checkPlayersState(match.currentPlayerPlaying,match.getOtherPlayer(),match.nbShots)
            this.fieldSides[0].desactivatePlayers()
            this.fieldSides[0].checkDrinksState(match)
        }
        checkShotButtonsConditions(match)
        checkFailedButtonCondition(match)
    }

    fun checkShotButtonsConditions(match: Match){
        shotButtons.forEach {
            shotButton ->  shotButton.checkConditions(match)
        }
    }

    fun checkFailedButtonCondition(match: Match){
        failedButton.checkConditions(match)
    }
}