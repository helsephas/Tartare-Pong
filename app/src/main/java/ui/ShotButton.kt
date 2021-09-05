package ui

import android.widget.Button
import models.Match
import models.shots.ShotType
import java.util.concurrent.Callable

class ShotButton(override var button: Button, var shotType: ShotType) : AbstractButton(button) {

    fun checkConditions(match: Match) {
        when (shotType) {
            ShotType.CALL ->
                hasAlreadyPlayed(match.currentPlayerHasAlreadyPlayed(), match.currentShotType,match.hasCurrentPlayerHasTrickShot(),match.currentDrinkIsCallable())
            ShotType.TRICK_SHOT ->
                hasAlreadyPlayed(match.currentPlayerHasAlreadyPlayed(), match.currentShotType,match.hasCurrentPlayerHasTrickShot(),match.currentDrinkIsCallable())
            ShotType.AIR_SHOT ->
                hasAlreadyPlayed(match.currentPlayerHasAlreadyPlayed(), match.currentShotType,match.hasCurrentPlayerHasTrickShot(),match.currentDrinkIsCallable())
            ShotType.BOUNCE ->
                hasAlreadyPlayed(match.currentPlayerHasAlreadyPlayed(), match.currentShotType,match.hasCurrentPlayerHasTrickShot(),match.currentDrinkIsCallable())
            ShotType.SIMPLE ->
                hasAlreadyPlayed(match.currentPlayerHasAlreadyPlayed(), match.currentShotType,match.hasCurrentPlayerHasTrickShot(),match.currentDrinkIsCallable())
        }
    }

    private fun hasAlreadyPlayed(hasAlreadyPlayed: Boolean, shotType: ShotType,hasCurrentPlayerHasTrickShot:Boolean, isDrinkCallable: Boolean) {
        when {
            this.shotType == shotType -> {
                selected()
            }
            !hasCurrentPlayerHasTrickShot && this.shotType != ShotType.TRICK_SHOT && this.shotType != ShotType.CALL -> {
                basic()
            }
            hasCurrentPlayerHasTrickShot && this.shotType == ShotType.TRICK_SHOT -> {
                basic()
            }
            isDrinkCallable && this.shotType == ShotType.CALL -> {
                basic()
            }
            else -> {
                disabled()
            }
        }
    }


}