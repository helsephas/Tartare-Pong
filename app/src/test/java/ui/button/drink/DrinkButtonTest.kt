package ui.button.drink

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

class DrinkButtonTest {

    private lateinit var drinkButton: DrinkButton
    private lateinit var match: Match
    private lateinit var buttonMocked: Button

    @BeforeEach
    fun setUp() {
        buttonMocked = mock(Button::class.java)
        drinkButton = DrinkButton(buttonMocked, 1)
        this.match = Match().startGame()
    }

    @Test
    fun testCheckButtonDefaultCondition() {
        drinkButton.checkDefenseDrinkState(match)
        verify(buttonMocked).setBackgroundResource(R.drawable.rounded_cirle)
        verify(buttonMocked).isEnabled = true
    }

    @Test
    fun testCheckButtonAirShotCondition() {
        match.currentShotType = ShotType.AIR_SHOT
        drinkButton.checkDefenseDrinkState(match)
        verify(buttonMocked).setBackgroundResource(R.drawable.rounded_cirle_red)
        verify(buttonMocked).isEnabled = false
    }

    @Test
    fun testCheckButtonSelectedCondition() {
        match.currentShotType = ShotType.SIMPLE
        match.addDrink(1)
        drinkButton.checkDefenseDrinkState(match)
        verify(buttonMocked).setBackgroundResource(R.drawable.rounded_cirle_green)
        verify(buttonMocked).isEnabled = false
    }

    @Test
    fun testCheckButtonTemporalyScoredAndSelectedCondition() {
        match.currentShotType = ShotType.SIMPLE
        match.addDrink(1)
        match.addShot()
        drinkButton.checkDefenseDrinkState(match)
        verify(buttonMocked).setBackgroundResource(R.drawable.rounded_cirle_green)
        verify(buttonMocked).isEnabled = false
    }

    @Test
    fun testCheckButtonDefinitvelyScoredCondition() {
        match.currentShotType = ShotType.SIMPLE
        match.addDrink(1)
        match.addShot()
        match.turnNumber = 2
        drinkButton.checkDefenseDrinkState(match)
        verify(buttonMocked).alpha = 0.0F
        verify(buttonMocked).isClickable = false
    }

    @Test
    fun testCheckButtonTemporalyScoredButNotSelectedCondition() {
        match.currentShotType = ShotType.SIMPLE
        match.addDrink(1)
        match.addShot()
        match.currentDrinkSelected = match.getDefenseTeam().getDrinkByNumber(2)
        drinkButton.checkDefenseDrinkState(match)
        verify(buttonMocked).setBackgroundResource(R.drawable.rounded_cirle_purple)
        verify(buttonMocked).isEnabled = true
    }

    @Test
    fun testCheckOtherTeamCondition() {
        drinkButton.checkAttackTeamDrinkState(match)
        verify(buttonMocked).setBackgroundResource(R.drawable.rounded_cirle_red)
        verify(buttonMocked).isEnabled = false
    }

    @Test
    fun testCheckOtherTeamScoredCondition() {
        match.currentShotType = ShotType.SIMPLE
        match.addDrink(1)
        match.addShot()
        match.currentPlayerPlaying = match.getDefenseTeam().getPlayerByNumber(1)
        match.turnNumber = 2
        drinkButton.checkAttackTeamDrinkState(match)
        verify(buttonMocked).alpha = 0.0F
        verify(buttonMocked).isClickable = false
    }

    @Test
    fun testCheckImpactToTreatAttackSelected() {
        match.selectDrinkForImpact(1, 1)
        drinkButton.checkAttackTeamDrinkState(match)
        verify(buttonMocked).setBackgroundResource(R.drawable.rounded_cirle_blue)
        verify(buttonMocked).isEnabled = true
    }

    @Test
    fun testCheckImpactToTreatAttackSelectable() {
        match.selectDrinkForImpact(1, 1)
        val drinkButton2: DrinkButton = DrinkButton(buttonMocked, 2)
        drinkButton2.checkAttackTeamDrinkState(match)
        verify(buttonMocked).setBackgroundResource(R.drawable.rounded_cirle_yellow)
        verify(buttonMocked).isEnabled = true
    }

    @Test
    fun testCheckImpactToTreatDefenseSelected() {
        match.selectDrinkForImpact(2, 1)
        drinkButton.checkDefenseDrinkState(match)
        verify(buttonMocked).setBackgroundResource(R.drawable.rounded_cirle_blue)
        verify(buttonMocked).isEnabled = true
    }

    @Test
    fun testCheckImpactToTreatDefenseSelectable() {
        match.selectDrinkForImpact(2, 1)
        val drinkButton2: DrinkButton = DrinkButton(buttonMocked, 2)
        drinkButton2.checkDefenseDrinkState(match)
        verify(buttonMocked).setBackgroundResource(R.drawable.rounded_cirle_yellow)
        verify(buttonMocked).isEnabled = true
    }

    @Test
    fun testCheckImpactToTreatDefenseScored() {
        match.addDrink(1)
        match.addShot()
        match.selectDrinkForImpact(2, 1)
        drinkButton.checkDefenseDrinkState(match)
        verify(buttonMocked).alpha = 0.0F
        verify(buttonMocked).isClickable = false
    }

    @Test
    fun testCheckImpactToTreatDefenseWhenTemporalyScored() {
        match.addDrink(1)
        match.addShot()
        match.addDrink(2)
        match.selectDrinkForImpact(2, 1)
        drinkButton.checkDefenseDrinkState(match)
        verify(buttonMocked).alpha = 0.0F
        verify(buttonMocked).isClickable = false
    }

    @AfterEach
    fun verifyMockInteractions() {
        verifyNoMoreInteractions(buttonMocked)
    }
}