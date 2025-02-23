package ui.button.shot

import android.widget.Button
import models.Match
import models.shots.ShotType
import ui.button.AbstractButton

class ShotButton(override var button: Button, var shotType: ShotType) : AbstractButton(button) {

    fun checkShotButtonState(match: Match) {
        when (shotType) {
            ShotType.CALL ->
                checkState(match)
            ShotType.TRICK_SHOT ->
               checkState(match)
            ShotType.AIR_SHOT ->
               checkState(match)
            ShotType.BOUNCE ->
               checkState(match)
            ShotType.SIMPLE ->
               checkState(match)
        }
    }

    private fun checkState(match: Match) {
        if(match.hasImpactedShotToTreat()){
            disabled()
        } else {
            when {
                match.currentShotTypeIs(this.shotType) -> {
                    selected()
                }
                match.isShotTypeAvailable(this.shotType)
                -> {
                    basic()
                }
                else -> {
                    disabled()
                }
            }
        }
    }


}