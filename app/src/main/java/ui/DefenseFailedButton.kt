package ui

import android.widget.Button
import com.example.tartarepong.R
import models.Match

class DefenseFailedButton(override var button: Button) : AbstractButton(button) {

    fun checkButtonCondition(match: Match) {
        if(match.hasDefender() && !match.isDrinkSelected()){
            display()
            if(match.hasFailedDefense){
                selected()
            } else {
                basic()
            }
        } else {
            notDisplay()
            match.hasNotFailedAnymore()
        }
    }

    override fun selected(){
        button.setBackgroundResource(R.drawable.rounded_cirle_green)
    }
}