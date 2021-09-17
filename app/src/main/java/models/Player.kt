package models

import java.math.BigDecimal

class Player( var id: BigDecimal = BigDecimal(0),
              var name: String = "", var number:Int, var teamNumber:Int) {

    var hasPlayed: Boolean = false
    var trickShotAvailable:Boolean = false

    override fun toString(): String {
        return "Player(id=$id, name='$name', number=$number, teamNumber=$teamNumber, trickShotAvailable=$trickShotAvailable)"
    }


}