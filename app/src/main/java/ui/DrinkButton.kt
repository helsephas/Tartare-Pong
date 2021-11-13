package ui

import android.widget.Button
import com.example.tartarepong.R
import models.Drink
import models.Match

class DrinkButton(override var button: Button, var teamNumber: Int, var drinkNumber: Int) : AbstractButton(button) {

    fun scored() {
        button.setBackgroundResource(R.drawable.rounded_cirle_purple)
        button.isEnabled = true
    }

    fun checkButtonCondition(match: Match) {
        if(match.isDrinkScored(drinkNumber) && !match.isDrinkNumberSelected(drinkNumber)) {
            scored()
        } else if(match.isDrinkScoredDefinitivelyScoredForCurrentTeam(drinkNumber)){
            notDisplay()
        } else if (match.isDrinkNumberSelected(drinkNumber)) {
            selected()
        }else if(match.currentShotType.isAirShot()){
            disabled()
        } else {
            basic()
        }
    }

    fun checkDisableCondition(match: Match){
        if(match.isDrinkScoredDefinitivelyScoredForOtherTeam(drinkNumber)){
            notDisplay()
        } else {
            disabled()
        }
    }


}