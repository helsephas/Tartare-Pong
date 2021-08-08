package models.shots

import models.Drink
import models.Player
import models.Shot
import models.ShotType

class SimpleShot(shotedBy: Player, var isSuccess: Boolean = false, drink: Drink) : Shot(shotedBy, drink, ShotType.SIMPLE) {

}