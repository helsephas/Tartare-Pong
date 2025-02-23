package ui.button.action

import android.widget.Button
import com.example.tartarepong.R
import models.Match
import models.shots.ShotType
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions

class DefenseFailedButtonTest {

    private lateinit var defenseFailedButton: DefenseFailedButton
    private lateinit var match: Match
    private lateinit var buttonMocked: Button

    @BeforeEach
    fun setUp() {
        buttonMocked = mock(Button::class.java)
        defenseFailedButton = DefenseFailedButton(buttonMocked)
        this.match = Match().startGame()
    }

    @Test
    fun testCheckButtonDefaultCondition() {
        defenseFailedButton.checkButtonState(match)
        verify(buttonMocked).alpha = 0.0F
        verify(buttonMocked).isClickable = false
    }

    @Test
    fun testCheckButtonDefenseFailedCondition() {
        match.currentShotType = ShotType.AIR_SHOT
        match.changeDefenderPlayerTo(1)
        defenseFailedButton.checkButtonState(match)
        verify(buttonMocked).alpha = 1.0F
        verify(buttonMocked).isClickable = true
        verify(buttonMocked).isEnabled = true
        verify(buttonMocked).setBackgroundResource(R.drawable.rounded_cirle)
    }

    @Test
    fun testCheckButtonDefenseSucceedCondition() {
        match.currentShotType = ShotType.AIR_SHOT
        match.changeDefenderPlayerTo(1)
        match.hasFailedDefense = true
        defenseFailedButton.checkButtonState(match)
        verify(buttonMocked).alpha = 1.0F
        verify(buttonMocked).isClickable = true
        verify(buttonMocked).setBackgroundResource(R.drawable.rounded_cirle_green)
    }

    @AfterEach
    fun verifyMockInteractions() {
        verifyNoMoreInteractions(buttonMocked)
    }
}