package ui.button.player

import android.graphics.Color
import android.widget.Button
import models.Match
import models.shots.ShotType
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions

class PlayerButtonTest {

    private lateinit var playerButton: PlayerButton
    private lateinit var player2Button: PlayerButton
    private lateinit var match: Match
    private lateinit var buttonMocked: Button

    @BeforeEach
    fun setUp() {
        buttonMocked = mock(Button::class.java)
        playerButton = PlayerButton(buttonMocked, 1)
        player2Button = PlayerButton(buttonMocked, 2)
        this.match = Match().startGame()
    }

    @Test
    fun testCheckOtherTeamDefaultCondition() {
        playerButton.checkDefenserPlayerState(match)
        verify(buttonMocked).setBackgroundColor(Color.RED)
        verify(buttonMocked).isEnabled = false
    }

    @Test
    fun testCheckOtherTeamDefendableCondition() {
        match.currentShotType = ShotType.AIR_SHOT
        playerButton.checkDefenserPlayerState(match)
        verify(buttonMocked).setBackgroundColor(Color.CYAN)
        verify(buttonMocked).isEnabled = true
    }

    @Test
    fun testCheckOtherTeamDefendedCondition() {
        match.currentShotType = ShotType.AIR_SHOT
        match.changeDefenderPlayerTo(1)
        playerButton.checkDefenserPlayerState(match)
        verify(buttonMocked).setBackgroundColor(Color.MAGENTA)
        verify(buttonMocked).isEnabled = true
    }

    @Test
    fun testCheckButtonDefaultConditions() {
        playerButton.checkAttackTeamPlayerState(match)
        verify(buttonMocked).setBackgroundColor(Color.GREEN)
        verify(buttonMocked).isEnabled = false
    }

    @Test
    fun testCheckButtonSecondPlayerConditions() {
        player2Button.checkAttackTeamPlayerState(match)
        verify(buttonMocked).setBackgroundColor(Color.BLACK)
        verify(buttonMocked).isEnabled = true
    }

    @Test
    fun testCheckButtonSecondPlayerAfterShotSucceedConditions() {
        match.currentPlayerPlaying = match.getAttackTeam().getPlayerByNumber(2)
        match.addDrink(1)
        match.addShot()
        match.currentPlayerPlaying = match.getAttackTeam().getPlayerByNumber(1)
        player2Button.checkAttackTeamPlayerState(match)
        verify(buttonMocked).setBackgroundColor(Color.RED)
        verify(buttonMocked).isEnabled = false
    }

    @Test
    fun testCheckButtonSecondPlayerAfterShotFailedConditions() {
        match.currentPlayerPlaying = match.getAttackTeam().getPlayerByNumber(2)
        match.addShot()
        match.currentPlayerPlaying = match.getAttackTeam().getPlayerByNumber(1)
        player2Button.checkAttackTeamPlayerState(match)
        verify(buttonMocked).setBackgroundColor(Color.BLUE)
        verify(buttonMocked).isEnabled = true
    }

    @Test
    fun testCheckButtonAfterTrickShotDone() {
        match.addShot()
        match.currentShotType = ShotType.TRICK_SHOT
        match.currentPlayerPlaying = match.getAttackTeam().getPlayerByNumber(1)
        match.addShot()
        playerButton.checkAttackTeamPlayerState(match)
        verify(buttonMocked).setBackgroundColor(Color.RED)
        verify(buttonMocked).isEnabled = false
    }

    @Test
    fun testCheckButtonFirstPlayerAfterShotFailed() {
        match.addShot()
        playerButton.checkAttackTeamPlayerState(match)
        verify(buttonMocked).setBackgroundColor(Color.BLUE)
        verify(buttonMocked).isEnabled = true
    }

    @Test
    fun testCheckButtonFirstPlayerAfterShotToo() {
        match.addShot()
        match.addShot()
        playerButton.checkAttackTeamPlayerState(match)
        verify(buttonMocked).setBackgroundColor(Color.RED)
        verify(buttonMocked).isEnabled = false
    }

    @Test
    fun testCheckButtonSecondPlayerAfterShotToo() {
        match.addShot()
        match.addShot()
        player2Button.checkAttackTeamPlayerState(match)
        verify(buttonMocked).setBackgroundColor(Color.GREEN)
        verify(buttonMocked).isEnabled = false
    }

    @Test
    fun testCheckButtonImpactToTreatAttack() {
        match.selectDrinkForImpact(1, 1)
        playerButton.checkAttackTeamPlayerState(match)
        verify(buttonMocked).setBackgroundColor(Color.RED)
        verify(buttonMocked).isEnabled = false
    }

    @Test
    fun testCheckButtonImpactToTreatDefense() {
        match.selectDrinkForImpact(1, 1)
        playerButton.checkDefenserPlayerState(match)
        verify(buttonMocked).setBackgroundColor(Color.RED)
        verify(buttonMocked).isEnabled = false
    }

    @AfterEach
    fun verifyMockInteractions() {
        verifyNoMoreInteractions(buttonMocked)
    }

}