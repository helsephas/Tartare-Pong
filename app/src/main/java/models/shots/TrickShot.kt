package models.shots

import models.Drink
import models.Player
import models.Shot
import models.ShotType

class TrickShot(shotedBy: Player, var isSuccess: Boolean = false, drink: Drink) : Shot(shotedBy, drink, ShotType.TRICK_SHOT) {

}