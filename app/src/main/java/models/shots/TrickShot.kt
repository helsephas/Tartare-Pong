package models.shots

import models.Drink
import models.Player

class TrickShot(shotedBy: Player ,var drink: Drink?, turnNumber:Int) : Shot(shotedBy,  ShotType.TRICK_SHOT,drink != null,turnNumber,1) {

    fun getCombinations():List<ShotType>{
        return listOf(ShotType.CALL,ShotType.BOUNCE)
    }
}