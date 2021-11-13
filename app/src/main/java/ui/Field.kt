package ui

import models.Match

class Field(private var fieldSides: MutableList<FieldSide> = arrayListOf(), private var shotButtons:MutableList<ShotButton> = arrayListOf()) {

    private lateinit var failedButton: FailedButton
    private lateinit var defenseFailedButton: DefenseFailedButton

    fun init(playerButtonTeamA: MutableList<PlayerButton>, playerButtonTeamB: MutableList<PlayerButton>,
            drinksButtonTeamA:MutableList<DrinkButton>, drinksButtonTeamB: MutableList<DrinkButton>,
             shotButtons:MutableList<ShotButton>, failedButton: FailedButton, defenseFailedButton: DefenseFailedButton) {
        this.fieldSides.add(FieldSide(1,playerButtonTeamA,drinksButtonTeamA))
        this.fieldSides.add(FieldSide(2,playerButtonTeamB,drinksButtonTeamB))
        this.shotButtons = shotButtons
        this.failedButton = failedButton
        this.defenseFailedButton = defenseFailedButton
    }

    fun checkFieldConfiguration(match: Match){
        currentTeamPlaying(match)
        otherTeamPlaying(match)
        checkShotButtonsConditions(match)
        checkFailedButtonCondition(match)
        checkDefenseFailedButtonConditions(match)
    }

    private fun currentTeamPlaying(match: Match){
        getFieldSide(match.getCurrentTeam().number).desactivateDrinks(match)
        getFieldSide(match.getCurrentTeam().number).checkPlayersState(match)
    }

    private fun otherTeamPlaying(match: Match){
        getFieldSide(match.getOtherTeam().number).checkOtherPlayersState(match)
        getFieldSide(match.getOtherTeam().number).checkDrinksState(match)
    }

    private fun checkShotButtonsConditions(match: Match){
        shotButtons.forEach {
            shotButton ->  shotButton.checkConditions(match)
        }
    }

    private fun checkFailedButtonCondition(match: Match){
        failedButton.checkConditions(match)
    }

    private fun checkDefenseFailedButtonConditions(match: Match){
        defenseFailedButton.checkButtonCondition(match)
    }

    private fun getFieldSide(teamNumber: Int):FieldSide{
        return fieldSides.filter { fieldSide  -> fieldSide.teamNumber == teamNumber}.toList()[0]
    }
}