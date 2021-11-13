package models.shots

import models.Drink
import models.Player

class TrickShot(shotedBy: Player, isSuccess: Boolean ,var drink: Drink?, turnNumber:Int) : Shot(shotedBy,  ShotType.TRICK_SHOT,isSuccess,turnNumber,1) {

    fun getCombinations():List<ShotType>{
        return listOf(ShotType.CALL,ShotType.BOUNCE)
    }
}