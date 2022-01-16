package models

import java.math.BigDecimal

class Player( var id: BigDecimal = BigDecimal(0),
              var name: String = "", var number:Int, var teamNumber:Int) {

    var hasPlayed: Boolean = false
    var trickShotAvailable:Boolean = false

    override fun toString(): String {
        return "Player(id=$id, name='$name', number=$number, teamNumber=$teamNumber, trickShotAvailable=$trickShotAvailable)"
    }
    
    fun changeToPlayedState(){
        this.hasPlayed = true
        if (this.trickShotAvailable) {
            this.trickShotAvailable = false
        }
    }

    fun changeToTrickShot(){
        this.trickShotAvailable = true
    }

    fun resetTurn(){
        hasPlayed = false
        trickShotAvailable = false
    }

    override fun equals(other: Any?): Boolean {
        val player:Player = other as Player
        return player.number == this.number && this.teamNumber == player.teamNumber
    }


}