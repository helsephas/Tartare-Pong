package ui.button.drink

import android.widget.Button
import com.example.tartarepong.R
import models.Match
import models.shots.ShotType
import ui.button.AbstractButton

class DrinkButton(override var button: Button, var drinkNumber: Int) : AbstractButton(button) {

    private fun scored() {
        button.setBackgroundResource(R.drawable.rounded_cirle_purple)
        button.isEnabled = true
    }

    private fun selectableForImpact() {
        button.setBackgroundResource(R.drawable.rounded_cirle_yellow)
        button.isEnabled = true
    }

    private fun selectedForImpact() {
        button.setBackgroundResource(R.drawable.rounded_cirle_blue)
        button.isEnabled = true
    }

    fun checkDefenseDrinkState(match: Match) {
        when {
            match.isDrinkTemporalyScored(drinkNumber) -> {
                if (match.hasImpactedShotToTreat(match.getDefenseTeam().number)) {
                    notDisplay()
                } else {
                    scored()
                }
            }
            match.isDrinkScoredDefinitivelyScoredForCurrentTeam(drinkNumber) -> {
                notDisplay()
            }
            match.hasImpactedShotToTreat(match.getDefenseTeam().number) -> {
                if(match.isDrinkScoredScoredThisTurnForCurrentTeam(drinkNumber)){
                    notDisplay()
                } else if (match.isDrinkSelectedForImpact(match.getDefenseTeam().number,drinkNumber)) {
                    selectedForImpact()
                } else {
                    selectableForImpact()
                }
            }
            match.isDrinkNumberSelected(drinkNumber) -> {
                selected()
            }
            match.currentShotTypeIs(ShotType.AIR_SHOT) -> {
                disabled()
            }
            else -> {
                basic()
            }
        }
    }

    fun checkAttackTeamDrinkState(match: Match) {
        if (match.isDrinkScoredDefinitivelyScoredForOtherTeam(drinkNumber)) {
            notDisplay()
        } else if (match.hasImpactedShotToTreat(match.getAttackTeam().number)) {
            if (match.isDrinkSelectedForImpact(match.getAttackTeam().number,drinkNumber)) {
                selectedForImpact()
            } else {
                selectableForImpact()
            }
        } else {
            disabled()
        }
    }
}
