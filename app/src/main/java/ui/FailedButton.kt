package ui

import android.widget.Button
import android.widget.ImageButton
import models.Match

class FailedButton(override var imageButton: ImageButton): AbstractImageButton(imageButton) {

    fun checkConditions(match: Match) {
        if(match.isDrinkSelected()){
            basic()
        } else {
            selected()
        }
    }

}