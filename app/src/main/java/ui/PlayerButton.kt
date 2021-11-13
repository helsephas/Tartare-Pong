package ui

import android.graphics.Color
import android.widget.Button
import models.Match
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

    private fun selectableForDefense(){
        this.button.setBackgroundColor(Color.CYAN)
        button.isEnabled = true
    }

    private fun selectedForDefense(){
        this.button.setBackgroundColor(Color.MAGENTA)
        button.isEnabled = true
    }

    fun checkOtherTeamCondition(match: Match){
        if(match.hasDefender() && match.defenderPlayer()?.number == playerNumber){
            selectedForDefense()
        } else if(match.defenderAvailable()){
            selectableForDefense()
        } else {
            desactivateSelection()
        }
    }

    fun desactivateSelection() {
        this.button.setBackgroundColor(Color.RED)
        button.isEnabled = false
    }

    fun checkButtonConditions(match: Match) {
        val currentPlayer: Player = match.currentPlayerPlaying
        val otherPlayer:Player = match.getOtherPlayer()
        val nbShots:Int = match.nbShots

        if (currentPlayer.number == playerNumber) {
            activateShot()
        } else {
            if (playerNumber == otherPlayer.number && otherPlayer.trickShotAvailable) {
                selectableForTrickShot()
            } else if (playerNumber == otherPlayer.number && !otherPlayer.hasPlayed) {
                selectable()
            } else {
                desactivateSelection()
            }
        }

    }
}