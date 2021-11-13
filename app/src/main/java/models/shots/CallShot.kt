package models.shots

import models.Drink
import models.Player

class CallShot(shotedBy: Player, isSuccess: Boolean ,var drink: Drink?, turnNumber:Int) : Shot(shotedBy, ShotType.CALL,isSuccess,turnNumber,2) {

    fun getCombinations():List<ShotType>{
        return listOf(ShotType.SIMPLE,ShotType.TRICK_SHOT,ShotType.BOUNCE)
    }
}