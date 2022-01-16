package models.shots

import models.Drink
import models.Player

class SimpleShot(shotedBy: Player,var drink: Drink?, turnNumber:Int) : Shot(shotedBy, ShotType.SIMPLE,drink != null,turnNumber,1) {

}