package models

import java.util.*

enum class ShotType {
    SIMPLE,
    BOUNCE,
    CALL,
    AIR_SHOT,
    TRICK_SHOT;

    fun canBeSucced(): Boolean {
        return listOf(SIMPLE,BOUNCE,CALL,TRICK_SHOT).contains(this);
    }
}