package ui.button.action

import android.widget.ImageButton
import models.Match
import ui.button.AbstractImageButton

class FailedButton(override var imageButton: ImageButton): AbstractImageButton(imageButton) {

    fun checkState(match: Match) {
        if(match.isDrinkSelected()){
            basic()
        } else if(match.hasImpactedShotToTreat()){
            disabled()
        } else {
            selected()
        }
    }

}