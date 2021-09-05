package ui

import android.graphics.Color
import android.widget.Button
import models.Player

class PlayerButton(var button: Button, var playerNumber: Int) {


    private fun activateShot() {
        this.button.setBackgroundColor(Color.GREEN)
        button.isEnabled = true
    }

    private fun selectable() {
        this.button.setBackgroundColor(Color.BLACK)
        button.isEnabled = true
    }

    private fun selectableForTrickShot() {
        this.button.setBackgroundColor(Color.BLUE)
        button.isEnabled = true
    }

    fun desactivateSelection() {
        this.button.setBackgroundColor(Color.RED)
        button.isEnabled = false
    }

    fun checkButtonConditions(currentPlayer: Player,otherPlayer:Player,nbShots:Int) {
        if (currentPlayer.number == playerNumber) {
            activateShot()
        } else {
            if (playerNumber == otherPlayer.number && otherPlayer.trickShotAvailable) {
                selectableForTrickShot()
            } else if (playerNumber == otherPlayer.number && currentPlayer.trickShotAvailable) {
                selectable()
            } else {
                desactivateSelection()
            }
        }

    }
}