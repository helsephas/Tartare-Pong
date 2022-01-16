package ui

import android.widget.Button
import junit.framework.TestCase
import models.Match
import models.shots.ShotType
import org.mockito.Mockito.*

class DrinkButtonTest : TestCase() {

    private lateinit var drinkButton: DrinkButton
    private lateinit var match: Match
    private lateinit var buttonMocked: Button

    public override fun setUp() {
        super.setUp()
        buttonMocked = mock(Button::class.java)
        drinkButton = DrinkButton(buttonMocked,1)
        this.match = Match().startGame()
    }

    fun testCheckButtonDefaultCondition() {
        drinkButton.checkDefenseDrinkState(match)
        verify(buttonMocked).setBackgroundResource(anyInt())
        verify(buttonMocked).isEnabled = true
    }

    fun testCheckButtonAirShotCondition() {
        match.currentShotType = ShotType.AIR_SHOT
        drinkButton.checkDefenseDrinkState(match)
        verify(buttonMocked).setBackgroundResource(anyInt())
        verify(buttonMocked).isEnabled = false
    }

    fun testCheckButtonSelectedCondition() {
        match.currentShotType = ShotType.SIMPLE
        match.addDrink(1)
        drinkButton.checkDefenseDrinkState(match)
        verify(buttonMocked).setBackgroundResource(anyInt())
        verify(buttonMocked).isEnabled = false
    }

    fun testCheckButtonTemporalyScoredAndSelectedCondition() {
        match.currentShotType = ShotType.SIMPLE
        match.addDrink(1)
        match.addShot()
        drinkButton.checkDefenseDrinkState(match)
        verify(buttonMocked).setBackgroundResource(anyInt())
        verify(buttonMocked).isEnabled = false
    }

    fun testCheckButtonDefinitvelyScoredCondition() {
        match.currentShotType = ShotType.SIMPLE
        match.addDrink(1)
        match.addShot()
        match.turnNumber = 2
        drinkButton.checkDefenseDrinkState(match)
        verify(buttonMocked).alpha = 0.0F
        verify(buttonMocked).isClickable = false
    }

    fun testCheckButtonTemporalyScoredButNotSelectedCondition() {
        match.currentShotType = ShotType.SIMPLE
        match.addDrink(1)
        match.addShot()
        match.currentDrinkSelected = match.getDefenseTeam().getDrinkByNumber(2)
        drinkButton.checkDefenseDrinkState(match)
        verify(buttonMocked).setBackgroundResource(anyInt())
        verify(buttonMocked).isEnabled = true
    }

    fun testCheckOtherTeamCondition() {
        drinkButton.checkAttackTeamDrinkState(match)
        verify(buttonMocked).setBackgroundResource(anyInt())
        verify(buttonMocked).isEnabled = false
    }

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
}