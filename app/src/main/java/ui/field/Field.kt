package ui.field

import models.Match
import ui.button.action.DefenseFailedButton
import ui.button.action.FailedButton
import ui.button.action.NextTurnButton
import ui.button.drink.DrinkButton
import ui.button.player.PlayerButton
import ui.button.shot.ShotButton

class Field(private var fieldSides: MutableList<FieldSide> = arrayListOf(), private var shotButtons:MutableList<ShotButton> = arrayListOf()) {

    private lateinit var failedButton: FailedButton
    private lateinit var defenseFailedButton: DefenseFailedButton
    private lateinit var nextTurnButton: NextTurnButton

    fun init(playerButtonsTeamA: MutableList<PlayerButton>, playerButtonsTeamB: MutableList<PlayerButton>,
             drinksButtonTeamA:MutableList<DrinkButton>, drinksButtonTeamB: MutableList<DrinkButton>,
             shotButtons:MutableList<ShotButton>, failedButton: FailedButton, defenseFailedButton: DefenseFailedButton, nextTurnButton: NextTurnButton
    ) {
        this.fieldSides.add(FieldSide(1,playerButtonsTeamA,drinksButtonTeamA))
        this.fieldSides.add(FieldSide(2,playerButtonsTeamB,drinksButtonTeamB))
        this.shotButtons = shotButtons
        this.failedButton = failedButton
        this.defenseFailedButton = defenseFailedButton
        this.nextTurnButton = nextTurnButton
    }

    fun checkFieldConfiguration(match: Match){
        attackTeamConfigfuration(match)
        defenseTeamConfiguration(match)
        checkShotButtonsState(match)
        checkFailedButtonState(match)
        checkDefenseFailedButtonConditions(match)
        checkNextTurnButtonState(match)
    }

    private fun checkNextTurnButtonState(match: Match) {
        nextTurnButton.checkButtonState(match)
    }

    private fun attackTeamConfigfuration(match: Match){
        getFieldSide(match.getAttackTeam().number).checkAttackTeamDrinksState(match)
        getFieldSide(match.getAttackTeam().number).checkAttackTeamPlayerState(match)
    }

    private fun defenseTeamConfiguration(match: Match){
        getFieldSide(match.getDefenseTeam().number).checkDefenserPlayersStates(match)
        getFieldSide(match.getDefenseTeam().number).checkDefenseDrinksState(match)
    }

    private fun checkShotButtonsState(match: Match){
        shotButtons.forEach {
            shotButton ->  shotButton.checkShotButtonState(match)
        }
    }

    private fun checkFailedButtonState(match: Match){
        failedButton.checkState(match)
    }

    private fun checkDefenseFailedButtonConditions(match: Match){
        defenseFailedButton.checkButtonState(match)
    }

    private fun getFieldSide(teamNumber: Int): FieldSide {
        return fieldSides.filter{ fieldSide  -> fieldSide.teamNumber == teamNumber}.toList().first()
    }
}