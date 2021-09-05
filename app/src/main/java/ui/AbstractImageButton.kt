package ui

import android.widget.Button
import android.widget.ImageButton
import com.example.tartarepong.R

abstract class AbstractImageButton(open var imageButton: ImageButton) {

    fun basic() {
        imageButton.setBackgroundResource(R.drawable.rounded_cirle)
        imageButton.isEnabled = true
    }

    fun selected() {
        imageButton.setBackgroundResource(R.drawable.rounded_cirle_green)
        imageButton.isEnabled = false
    }

    fun disabled() {
        imageButton.setBackgroundResource(R.drawable.rounded_cirle_red)
        imageButton.isEnabled = false
    }

}