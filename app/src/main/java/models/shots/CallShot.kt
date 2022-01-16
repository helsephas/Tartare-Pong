package models.shots

import models.Drink
import models.Player

class CallShot(shotedBy: Player ,var drink: Drink?, turnNumber:Int) : Shot(shotedBy, ShotType.CALL,drink != null,turnNumber,2) {

    fun getCombinations():List<ShotType>{
        return listOf(ShotType.TRICK_SHOT,ShotType.BOUNCE)
    }
}