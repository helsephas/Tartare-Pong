package ui

import android.widget.Button
import junit.framework.TestCase
import models.Match
import models.shots.ShotType
import org.junit.Test
import org.mockito.Mockito.*

class ShotButtonTest : TestCase() {

    private lateinit var simpleShot: ShotButton
    private lateinit var airShot: ShotButton
    private lateinit var bounceShot: ShotButton
    private lateinit var trickShot: ShotButton
    private lateinit var callShot: ShotButton
    private lateinit var match: Match
    private lateinit var buttonMocked: Button

    public override fun setUp() {
        super.setUp()
        buttonMocked = mock(Button::class.java)
        simpleShot = ShotButton(buttonMocked,ShotType.SIMPLE)
        airShot = ShotButton(buttonMocked,ShotType.AIR_SHOT)
        bounceShot = ShotButton(buttonMocked,ShotType.BOUNCE)
        trickShot = ShotButton(buttonMocked,ShotType.TRICK_SHOT)
        callShot = ShotButton(buttonMocked,ShotType.CALL)
        this.match = Match().startGame()
    }

    @Test
    fun testCheckSimpleShotConditions() {
        simpleShot.checkShotButtonState(match)

        verify(buttonMocked).setBackgroundResource(anyInt())
        verify(buttonMocked).isEnabled = false
    }

    @Test
    fun testCheckTrickShotConditions() {
        trickShot.checkShotButtonState(match)

        verify(buttonMocked).setBackgroundResource(anyInt())
        verify(buttonMocked).isEnabled = false
    }

    @Test
    fun testCheckBounceShotConditions() {
        bounceShot.checkShotButtonState(match)

        verify(buttonMocked).setBackgroundResource(anyInt())
        verify(buttonMocked).isEnabled = true
    }

    @Test
    fun testCheckCallConditions(){
        callShot.checkShotButtonState(match)

        verify(buttonMocked).setBackgroundResource(anyInt())
        verify(buttonMocked).isEnabled = false
    }

    @Test
    fun testCheckAirShotCondition(){
        airShot.checkShotButtonState(match)

        verify(buttonMocked).setBackgroundResource(anyInt())
        verify(buttonMocked).isEnabled = true
    }

    @Test
    fun testCheckCallAvailable(){
        match.getDefenseTeam().getDrinkByNumber(2).isDone = true
        match.getDefenseTeam().getDrinkByNumber(3).isDone = true
        match.addDrink(1)

        callShot.checkShotButtonState(match)

        verify(buttonMocked).setBackgroundResource(anyInt())
        verify(buttonMocked).isEnabled = true
    }

    @Test
    fun testCheckCallSelected(){
        match.currentShotType = ShotType.CALL
        match.getDefenseTeam().getDrinkByNumber(2).isDone = true
        match.getDefenseTeam().getDrinkByNumber(3).isDone = true
        match.addDrink(1)

        callShot.checkShotButtonState(match)

        verify(buttonMocked).setBackgroundResource(anyInt())
        verify(buttonMocked).isEnabled = false
    }

    @Test
    fun testCheckTrickotAvailable(){
        match.addShot()
        match.currentShotType = ShotType.TRICK_SHOT
        match.currentPlayerPlaying = match.getAttackTeam().getPlayerByNumber(1)

        trickShot.checkShotButtonState(match)

        verify(buttonMocked).setBackgroundResource(anyInt())
        verify(buttonMocked).isEnabled = false
    }



}