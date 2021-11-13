package ui

import android.widget.Button
import com.example.tartarepong.R

abstract class AbstractButton(open var button: Button) {

    fun basic() {
        button.setBackgroundResource(R.drawable.rounded_cirle)
        button.isEnabled = true
    }

    open fun selected() {
        button.setBackgroundResource(R.drawable.rounded_cirle_green)
        button.isEnabled = false
    }

    fun disabled() {
        button.setBackgroundResource(R.drawable.rounded_cirle_red)
        button.isEnabled = false
    }

    fun notDisplay() {
        button.alpha = 0.0F
        button.isClickable = false
    }

    fun display() {
        button.alpha = 1.0F
        button.isClickable = true
    }

}
