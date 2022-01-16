package ui

import android.widget.Button
import android.widget.ImageButton
import com.example.tartarepong.R
import models.Match
import models.shots.ShotType

class FailedButton(override var imageButton: ImageButton): AbstractImageButton(imageButton) {

    fun checkState(match: Match) {
        if(match.isDrinkSelected()){
            basic()
        }else {
            selected()
        }
    }

}