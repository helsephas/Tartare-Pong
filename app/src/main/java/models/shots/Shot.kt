package models.shots

import models.Player

abstract class Shot(var shotedBy: Player, val shotType: ShotType, var isSuccess: Boolean, var turnNumber:Int, var shotImpact:Int) {


    fun hasImpact():Boolean{
        return shotImpact > 1
    }

    override fun toString(): String {
        return "Shot(shotedBy=$shotedBy, shotType=$shotType, isSuccess=$isSuccess, turnNumber=$turnNumber\n)"
    }
}