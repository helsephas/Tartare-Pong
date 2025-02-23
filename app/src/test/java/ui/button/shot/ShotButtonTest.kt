package ui.button.shot

import android.widget.Button
import com.example.tartarepong.R
import models.Match
import models.shots.ShotType
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions

class ShotButtonTest {

    private lateinit var simpleShot: ShotButton
    private lateinit var airShot: ShotButton
    private lateinit var bounceShot: ShotButton
    private lateinit var trickShot: ShotButton
    private lateinit var callShot: ShotButton
    private lateinit var match: Match
    private lateinit var buttonMocked: Button

    @BeforeEach
    fun setUp() {
        buttonMocked = mock(Button::class.java)
        simpleShot = ShotButton(buttonMocked, ShotType.SIMPLE)
        airShot = ShotButton(buttonMocked, ShotType.AIR_SHOT)
        bounceShot = ShotButton(buttonMocked, ShotType.BOUNCE)
        trickShot = ShotButton(buttonMocked, ShotType.TRICK_SHOT)
        callShot = ShotButton(buttonMocked, ShotType.CALL)
        this.match = Match().startGame()
    }

    @Test
    fun testCheckSimpleShotConditions() {
        simpleShot.checkShotButtonState(match)

        verify(buttonMocked).setBackgroundResource(R.drawable.rounded_cirle_green)
        verify(buttonMocked).isEnabled = false
    }

    @Test
    fun testCheckTrickShotConditions() {
        trickShot.checkShotButtonState(match)

        verify(buttonMocked).setBackgroundResource(R.drawable.rounded_cirle_red)
        verify(buttonMocked).isEnabled = false
    }

    @Test
    fun testCheckBounceShotConditions() {
        bounceShot.checkShotButtonState(match)

        verify(buttonMocked).setBackgroundResource(R.drawable.rounded_cirle)
        verify(buttonMocked).isEnabled = true
    }

    @Test
    fun testCheckCallConditions() {
        callShot.checkShotButtonState(match)

        verify(buttonMocked).setBackgroundResource(R.drawable.rounded_cirle_red)
        verify(buttonMocked).isEnabled = false
    }

    @Test
    fun testCheckAirShotCondition() {
        airShot.checkShotButtonState(match)

        verify(buttonMocked).setBackgroundResource(R.drawable.rounded_cirle)
        verify(buttonMocked).isEnabled = true
    }

    @Test
    fun testCheckCallAvailable() {
        match.getDefenseTeam().getDrinkByNumber(2).isDone = true
        match.getDefenseTeam().getDrinkByNumber(3).isDone = true
        match.addDrink(1)

        callShot.checkShotButtonState(match)

        verify(buttonMocked).setBackgroundResource(R.drawable.rounded_cirle)
        verify(buttonMocked).isEnabled = true
    }

    @Test
    fun testCheckCallSelected() {
        match.currentShotType = ShotType.CALL
        match.getDefenseTeam().getDrinkByNumber(2).isDone = true
        match.getDefenseTeam().getDrinkByNumber(3).isDone = true
        match.addDrink(1)

        callShot.checkShotButtonState(match)

        verify(buttonMocked).setBackgroundResource(R.drawable.rounded_cirle_green)
        verify(buttonMocked).isEnabled = false
    }

    @Test
    fun testCheckTrickotAvailable() {
        match.addShot()
        match.currentShotType = ShotType.TRICK_SHOT
        match.currentPlayerPlaying = match.getAttackTeam().getPlayerByNumber(1)

        trickShot.checkShotButtonState(match)

        verify(buttonMocked).setBackgroundResource(R.drawable.rounded_cirle_green)
        verify(buttonMocked).isEnabled = false
    }

    @Test
    fun testCheckButtonImpactToTreat() {
        match.selectDrinkForImpact(1, 1)
        simpleShot.checkShotButtonState(match)
        callShot.checkShotButtonState(match)
        airShot.checkShotButtonState(match)
        bounceShot.checkShotButtonState(match)
        trickShot.checkShotButtonState(match)
        verify(buttonMocked, times(5)).setBackgroundResource(R.drawable.rounded_cirle_red)
        verify(buttonMocked, times(5)).isEnabled = false
    }

    @AfterEach
    fun verifyMockInteractions() {
        verifyNoMoreInteractions(buttonMocked)
    }


}