package models.shots

import models.Drink
import models.Player
import models.Shot
import models.ShotType

class AirShot(shotedBy: Player, drink: Drink) : Shot(shotedBy, drink, ShotType.AIR_SHOT) {

    val isSuccess: Boolean = false
    lateinit var defender : Player
}