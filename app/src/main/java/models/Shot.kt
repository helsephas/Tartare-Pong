package models

class Shot(var shotedBy: Player, var shotType: ShotType = ShotType.SIMPLE, var isSuccess : Boolean = false) {

}