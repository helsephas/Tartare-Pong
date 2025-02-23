package models.shots

import models.Drink
import models.Player

class BounceShot(shotedBy: Player, var drink: Drink?, var defender:Player?, turnNumber:Int,defenseDone:Boolean = false)
    : Shot(shotedBy, ShotType.BOUNCE,drink != null && (defender == null && !defenseDone),turnNumber,1) {

    fun getCombinations():List<ShotType>{
        return listOf(ShotType.CALL,ShotType.TRICK_SHOT)
    }
}