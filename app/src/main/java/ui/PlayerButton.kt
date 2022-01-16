package ui

import android.graphics.Color
import android.widget.Button
import models.Match
import models.Player

class PlayerButton(var button: Button, var playerNumber: Int) {

    fun checkAttackTeamPlayerState(match: Match) {
        if (match.isPlayerCurrentPlayer(playerNumber)) {
            activateShot()
        } else {
            when {
                match.hasOtherPlayerTrickshotAvailable(playerNumber) -> {
                    selectableForTrickShot()
                }
                match.hasSecondPlayerNotAlreadyPlayed(playerNumber) -> {
                    selectable()
                }
                else -> {
                    desactivateSelection()
                }
            }
        }

    }

    fun checkDefenserPlayerState(match: Match) {
        when {
            match.defenderSelectedForDefense(playerNumber) -> {
                selectedForDefense()
            }
            match.defenderAvailable() -> {
                selectableForDefense()
            }
            else -> {
                desactivateSelection()
            }
        }
    }

    private fun activateShot() {
        this.button.setBackgroundColor(Color.GREEN)
        button.isEnabled = false
    }

    private fun selectable() {
        this.button.setBackgroundColor(Color.BLACK)
        button.isEnabled = true
    }

    private fun selectableForTrickShot() {
        this.button.setBackgroundColor(Color.BLUE)
        button.isEnabled = true
    }

    private fun selectableForDefense() {
        this.button.setBackgroundColor(Color.CYAN)
        button.isEnabled = true
    }

    private fun selectedForDefense() {
        this.button.setBackgroundColor(Color.MAGENTA)
        button.isEnabled = true
    }

    private fun desactivateSelection() {
        this.button.setBackgroundColor(Color.RED)
        button.isEnabled = false
    }


}