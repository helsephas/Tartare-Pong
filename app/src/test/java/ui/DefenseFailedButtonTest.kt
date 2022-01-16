package ui

import android.widget.Button
import junit.framework.TestCase
import models.Match
import models.shots.ShotType
import org.mockito.Mockito.*

class DefenseFailedButtonTest : TestCase() {

    private lateinit var defenseFailedButton: DefenseFailedButton
    private lateinit var match: Match
    private lateinit var buttonMocked: Button

    public override fun setUp() {
        super.setUp()
        buttonMocked = mock(Button::class.java)
        defenseFailedButton = DefenseFailedButton(buttonMocked)
        this.match = Match().startGame()
    }

    fun testCheckButtonDefaultCondition() {
        defenseFailedButton.checkButtonState(match)
        verify(buttonMocked).alpha = 0.0F
        verify(buttonMocked).isClickable = false
    }

    fun testCheckButtonDefenseFailedCondition() {
        match.currentShotType = ShotType.AIR_SHOT
        match.changeDefenderPlayerTo(1)
        defenseFailedButton.checkButtonState(match)
        verify(buttonMocked).alpha = 1.0F
        verify(buttonMocked).isClickable = true
        verify(buttonMocked).setBackgroundResource(anyInt())
    }

    fun testCheckButtonDefenseSucceedCondition() {
        match.currentShotType = ShotType.AIR_SHOT
        match.changeDefenderPlayerTo(1)
        match.hasFailedDefense = true
        defenseFailedButton.checkButtonState(match)
        verify(buttonMocked).alpha = 1.0F
        verify(buttonMocked).isClickable = true
        verify(buttonMocked).setBackgroundResource(anyInt())
    }
}