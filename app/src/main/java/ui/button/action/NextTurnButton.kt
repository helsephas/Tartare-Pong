package ui.button.action

import android.widget.Button
import models.Match
import ui.button.AbstractButton

class NextTurnButton(override var button: Button) : AbstractButton(button) {

    fun checkButtonState(match: Match) {
        if(match.has2PlayersAlreadyPlayed()){
            display()
        } else{
            notDisplay()
        }
    }

}