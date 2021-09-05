package models.shots

import models.Drink
import models.Player

class BounceShot(shotedBy: Player, isSuccess: Boolean ,var drink: Drink?,var defender:Player?, turnNumber:Int) : Shot(shotedBy, ShotType.BOUNCE,isSuccess,turnNumber) {

}