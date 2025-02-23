package ui.button.action

import android.widget.Button
import models.Match
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions

class NextTurnButtonTest {

    private lateinit var nextTurnButton: NextTurnButton
    private lateinit var match: Match
    private lateinit var buttonMocked: Button

    @BeforeEach
    fun setUp() {
        buttonMocked = mock(Button::class.java)
        this.nextTurnButton = NextTurnButton(buttonMocked)
        this.match = Match().startGame()
    }

    @Test
    fun checkButtonState() {
        nextTurnButton.checkButtonState(match)

        verify(buttonMocked).isClickable = false
        verify(buttonMocked).alpha = 0.0F
    }

    @Test
    fun checkButtonStateNextTurn() {
        match.addShot()
        match.addShot()
        nextTurnButton.checkButtonState(match)

        verify(buttonMocked).isClickable = true
        verify(buttonMocked).alpha = 1.0F
    }

    @AfterEach
    fun verifyMockInteractions() {
        verifyNoMoreInteractions(buttonMocked)
    }
}