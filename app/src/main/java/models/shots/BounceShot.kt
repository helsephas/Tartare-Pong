package models.shots

import models.Drink
import models.Player
import java.util.*

class BounceShot(shotedBy: Player, var drink: Drink?, var defender:Player?, turnNumber:Int,defenseDone:Boolean = false)
    : Shot(shotedBy, ShotType.BOUNCE,drink != null && (defender == null && !defenseDone),turnNumber,2) {

    fun getCombinations():List<ShotType>{
        return listOf(ShotType.CALL,ShotType.TRICK_SHOT)
    }
}