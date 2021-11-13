package models.shots

import models.Drink
import models.Player

class SimpleShot(shotedBy: Player, isSuccess: Boolean,var drink: Drink?, turnNumber:Int) : Shot(shotedBy, ShotType.SIMPLE,isSuccess,turnNumber,1) {

    fun getCombinations():List<ShotType>{
        return listOf(ShotType.CALL)
    }
}