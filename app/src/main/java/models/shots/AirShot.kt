package models.shots

import models.Player

class AirShot(shotedBy: Player,var defender:Player?,turnNumber:Int) : Shot(shotedBy, ShotType.AIR_SHOT,false,turnNumber,1) {

    fun hasBeenDefended():Boolean{
        return defender != null
    }
}