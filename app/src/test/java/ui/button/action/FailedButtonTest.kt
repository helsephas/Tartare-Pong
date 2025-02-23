package ui.button.action

import android.widget.ImageButton
import com.example.tartarepong.R
import models.Match
import models.shots.ShotType
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions

class FailedButtonTest {

    private lateinit var failedButton: FailedButton
    private lateinit var match: Match
    private lateinit var imageButtonMocked: ImageButton

    @BeforeEach
    fun setUp() {
        imageButtonMocked = mock(ImageButton::class.java)
        this.failedButton = FailedButton(imageButtonMocked)
        this.match = Match().startGame()
    }

    @Test
    fun testCheckDefaultConditions() {
        failedButton.checkState(match)
        verify(imageButtonMocked).setBackgroundResource(R.drawable.rounded_cirle_green)
        verify(imageButtonMocked).isEnabled = false
    }

    @Test
    fun testCheckAirShotConditions() {
        match.currentShotType = ShotType.AIR_SHOT
        failedButton.checkState(match)
        verify(imageButtonMocked).setBackgroundResource(R.drawable.rounded_cirle_green)
        verify(imageButtonMocked).isEnabled = false
    }

    @Test
    fun testCheckSeletableCondition() {
        match.addDrink(1)
        failedButton.checkState(match)
        verify(imageButtonMocked).setBackgroundResource(R.drawable.rounded_cirle)
        verify(imageButtonMocked).isEnabled = true
    }

    @Test
    fun testCheckDisabledBacauseOfImpactsShots() {
        match.selectDrinkForImpact(1, 1)
        failedButton.checkState(match)
        verify(imageButtonMocked).setBackgroundResource(R.drawable.rounded_cirle_red)
        verify(imageButtonMocked).isEnabled = false
    }

    @Test
    fun testCheckDisabledBacauseOfImpactsShotsOtherTeam() {
        match.selectDrinkForImpact(2, 1)
        failedButton.checkState(match)
        verify(imageButtonMocked).setBackgroundResource(R.drawable.rounded_cirle_red)
        verify(imageButtonMocked).isEnabled = false
    }

    @Test
    fun testCheckDisabledBacauseOfImpactsShotsBothTeam() {
        match.selectDrinkForImpact(1, 1)
        match.selectDrinkForImpact(2, 1)
        failedButton.checkState(match)
        verify(imageButtonMocked).setBackgroundResource(R.drawable.rounded_cirle_red)
        verify(imageButtonMocked).isEnabled = false
    }

    @AfterEach
    fun verifyMockInteractions() {
        verifyNoMoreInteractions(imageButtonMocked)
    }
}