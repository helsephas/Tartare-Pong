package ui

import android.widget.Button
import com.example.tartarepong.R
import models.Drink
import models.Match
import models.shots.ShotType

class DrinkButton(override var button: Button, var drinkNumber: Int) : AbstractButton(button) {

    private fun scored() {
        button.setBackgroundResource(R.drawable.rounded_cirle_purple)
        button.isEnabled = true
    }

    fun checkDefenseDrinkState(match: Match) {
        when {
            match.isDrinkTemporalyScored(drinkNumber) -> {
                scored()
            }
            match.isDrinkScoredDefinitivelyScoredForCurrentTeam(drinkNumber) -> {
                notDisplay()
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

    fun checkAttackTeamDrinkState(match: Match){
        if(match.isDrinkScoredDefinitivelyScoredForOtherTeam(drinkNumber)){
            notDisplay()
        } else {
            disabled()
        }
    }


}