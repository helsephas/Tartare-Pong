package models.shots

import models.Drink
import models.Player
import java.util.*

class BounceShot(shotedBy: Player, isSuccess: Boolean ,var drink: Drink?,var defender:Player?, turnNumber:Int) : Shot(shotedBy, ShotType.BOUNCE,isSuccess,turnNumber,2) {

    fun getCombinations():List<ShotType>{
        return listOf(ShotType.CALL,ShotType.TRICK_SHOT)
    }
}