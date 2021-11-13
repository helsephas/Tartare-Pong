package models.shots

enum class ShotType {
    SIMPLE,
    BOUNCE,
    CALL,
    AIR_SHOT,
    TRICK_SHOT;

    fun canBeSucced(): Boolean {
        return listOf(SIMPLE, BOUNCE, CALL, TRICK_SHOT).contains(this);
    }

    fun defendable():Boolean {
        return this == BOUNCE || this == AIR_SHOT
    }

    fun isAirShot():Boolean{
        return this == AIR_SHOT
    }
}