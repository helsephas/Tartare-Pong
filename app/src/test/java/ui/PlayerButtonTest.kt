package ui

import android.widget.Button
import junit.framework.TestCase
import models.Match
import models.shots.ShotType
import org.junit.Test
import org.mockito.Mockito.*

class PlayerButtonTest : TestCase() {

    private lateinit var playerButton: PlayerButton
    private lateinit var player2Button: PlayerButton
    private lateinit var match: Match
    private lateinit var buttonMocked: Button

    public override fun setUp() {
        super.setUp()
        buttonMocked = mock(Button::class.java)
        playerButton = PlayerButton(buttonMocked,1)
        player2Button = PlayerButton(buttonMocked,2)
        this.match = Match().startGame()
    }

    @Test
    fun testCheckOtherTeamDefaultCondition() {
        playerButton.checkDefenserPlayerState(match)
        verify(buttonMocked).setBackgroundColor(anyInt())
        verify(buttonMocked).isEnabled = false
    }

    @Test
    fun testCheckOtherTeamDefendableCondition() {
        match.currentShotType = ShotType.AIR_SHOT
        playerButton.checkDefenserPlayerState(match)
        verify(buttonMocked).setBackgroundColor(anyInt())
        verify(buttonMocked).isEnabled = true
    }

    @Test
    fun testCheckOtherTeamDefendedCondition() {
        match.currentShotType = ShotType.AIR_SHOT
        match.changeDefenderPlayerTo(1)
        playerButton.checkDefenserPlayerState(match)
        verify(buttonMocked).setBackgroundColor(anyInt())
        verify(buttonMocked).isEnabled = true
    }

    @Test
    fun testCheckButtonDefaultConditions() {
        playerButton.checkAttackTeamPlayerState(match)
        verify(buttonMocked).setBackgroundColor(anyInt())
        verify(buttonMocked).isEnabled = false
    }

    @Test
    fun testCheckButtonSecondPlayerConditions() {
        player2Button.checkAttackTeamPlayerState(match)
        verify(buttonMocked).setBackgroundColor(anyInt())
        verify(buttonMocked).isEnabled = true
    }

    @Test
    fun testCheckButtonSecondPlayerAfterShotSucceedConditions() {
        match.currentPlayerPlaying = match.getAttackTeam().getPlayerByNumber(2)
        match.addDrink(1)
        match.addShot()
        match.currentPlayerPlaying = match.getAttackTeam().getPlayerByNumber(1)
        player2Button.checkAttackTeamPlayerState(match)
        verify(buttonMocked).setBackgroundColor(anyInt())
        verify(buttonMocked).isEnabled = false
    }

    @Test
    fun testCheckButtonSecondPlayerAfterShotFailedConditions() {
        match.currentPlayerPlaying = match.getAttackTeam().getPlayerByNumber(2)
        match.addShot()
        match.currentPlayerPlaying = match.getAttackTeam().getPlayerByNumber(1)
        player2Button.checkAttackTeamPlayerState(match)
        verify(buttonMocked).setBackgroundColor(anyInt())
        verify(buttonMocked).isEnabled = true
    }

}