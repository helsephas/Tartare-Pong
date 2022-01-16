package ui

import android.widget.ImageButton
import junit.framework.TestCase
import models.Match
import models.shots.ShotType
import org.mockito.Mockito.*

class FailedButtonTest : TestCase() {

    private lateinit var failedButton: FailedButton
    private lateinit var match: Match
    private lateinit var imageButtonMocked:ImageButton

    override fun setUp() {
        super.setUp()
        imageButtonMocked = mock(ImageButton::class.java)
        this.failedButton = FailedButton(imageButtonMocked)
        this.match = Match().startGame()
    }

    fun testCheckDefaultConditions() {
        failedButton.checkState(match)
        verify(imageButtonMocked).setBackgroundResource(anyInt())
        verify(imageButtonMocked).isEnabled = false
    }

    fun testCheckAirShotConditions() {
        match.currentShotType = ShotType.AIR_SHOT
        failedButton.checkState(match)
        verify(imageButtonMocked).setBackgroundResource(anyInt())
        verify(imageButtonMocked).isEnabled = false
    }

    fun testCheckSeletableCondition() {
        match.addDrink(1)
        failedButton.checkState(match)
        verify(imageButtonMocked).setBackgroundResource(anyInt())
        verify(imageButtonMocked).isEnabled = true
    }
}