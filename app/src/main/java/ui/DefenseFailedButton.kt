package ui

import android.widget.Button
import com.example.tartarepong.R
import models.Match

class DefenseFailedButton(override var button: Button) : AbstractButton(button) {

    fun checkButtonState(match: Match) {
        if(match.hasDefender()){
            display()
            if(match.hasFailedDefense){
                selected()
            } else {
                basic()
            }
        } else {
            notDisplay()
        }
    }

    override fun selected(){
        button.setBackgroundResource(R.drawable.rounded_cirle_green)
    }
}