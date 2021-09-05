package models.shots

import models.Player

abstract class Shot(var shotedBy: Player, val shotType: ShotType, var isSuccess: Boolean, var turnNumber:Int) {


    override fun toString(): String {
        return "Shot(shotedBy=$shotedBy, shotType=$shotType, isSuccess=$isSuccess, turnNumber=$turnNumber\n)"
    }
}