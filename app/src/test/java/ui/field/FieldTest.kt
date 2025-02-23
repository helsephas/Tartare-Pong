package ui.field

import models.Match
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import ui.button.action.DefenseFailedButton
import ui.button.action.FailedButton
import ui.button.action.NextTurnButton
import ui.button.drink.DrinkButton
import ui.button.player.PlayerButton
import ui.button.shot.ShotButton

class FieldTest {

    private lateinit var field: Field

    private lateinit var playerButtonsTeamA: MutableList<PlayerButton>
    private lateinit var playerButtonsTeamB: MutableList<PlayerButton>
    private lateinit var drinksButtonTeamA: MutableList<DrinkButton>
    private lateinit var drinksButtonTeamB: MutableList<DrinkButton>
    private lateinit var shotButtons: MutableList<ShotButton>

    private lateinit var failedButton: FailedButton
    private lateinit var defenseFailedButton: DefenseFailedButton
    private lateinit var nextTurnButton: NextTurnButton

    private lateinit var playerButtonA: PlayerButton
    private lateinit var player2ButtonA: PlayerButton

    private lateinit var playerButtonB: PlayerButton
    private lateinit var player2ButtonB: PlayerButton

    private lateinit var drinkButtonA: DrinkButton
    private lateinit var drink2ButtonA: DrinkButton
    private lateinit var drink3ButtonA: DrinkButton
    private lateinit var drink4ButtonA: DrinkButton
    private lateinit var drink5ButtonA: DrinkButton
    private lateinit var drink6ButtonA: DrinkButton

    private lateinit var drinkButtonB: DrinkButton
    private lateinit var drink2ButtonB: DrinkButton
    private lateinit var drink3ButtonB: DrinkButton
    private lateinit var drink4ButtonB: DrinkButton
    private lateinit var drink5ButtonB: DrinkButton
    private lateinit var drink6ButtonB: DrinkButton

    private lateinit var callShotButton: ShotButton
    private lateinit var trickShotButton: ShotButton
    private lateinit var airShotButton: ShotButton
    private lateinit var bounceShotButton: ShotButton
    private lateinit var simpleShotButton: ShotButton

    private lateinit var match: Match

    @BeforeEach
    fun setUp() {
        playerButtonsTeamA = getPlayersA()
        playerButtonsTeamB = getPlayersB()
        drinksButtonTeamA = getDrinksA()
        drinksButtonTeamB = getDrinksB()
        shotButtons = getShots()
        failedButton = mock(FailedButton::class.java)
        defenseFailedButton = mock(DefenseFailedButton::class.java)
        nextTurnButton = mock(NextTurnButton::class.java)
        field = Field()
        field.init(playerButtonsTeamA, playerButtonsTeamB, drinksButtonTeamA, drinksButtonTeamB, shotButtons, failedButton, defenseFailedButton, nextTurnButton)
    }

    @Test
    fun init() {
        val fieldSidesField: java.lang.reflect.Field = Field::class.java.getDeclaredField("fieldSides")
        fieldSidesField.isAccessible = true
        @Suppress("UNCHECKED_CAST")
        val fieldSides: MutableList<FieldSide> = fieldSidesField.get(field) as MutableList<FieldSide>
        assertEquals(2, fieldSides.size)
    }

    @Test
    fun checkFieldConfiguration() {
        match = Match().startGame()
        field.checkFieldConfiguration(match)

        verify(playerButtonA).checkAttackTeamPlayerState(match)
        verify(player2ButtonA).checkAttackTeamPlayerState(match)

        verify(playerButtonB).checkDefenserPlayerState(match)
        verify(player2ButtonB).checkDefenserPlayerState(match)

        verify(drinkButtonA).checkAttackTeamDrinkState(match)
        verify(drink2ButtonA).checkAttackTeamDrinkState(match)
        verify(drink3ButtonA).checkAttackTeamDrinkState(match)
        verify(drink4ButtonA).checkAttackTeamDrinkState(match)
        verify(drink5ButtonA).checkAttackTeamDrinkState(match)
        verify(drink6ButtonA).checkAttackTeamDrinkState(match)

        verify(drinkButtonB).checkDefenseDrinkState(match)
        verify(drink2ButtonB).checkDefenseDrinkState(match)
        verify(drink3ButtonB).checkDefenseDrinkState(match)
        verify(drink4ButtonB).checkDefenseDrinkState(match)
        verify(drink5ButtonB).checkDefenseDrinkState(match)
        verify(drink6ButtonB).checkDefenseDrinkState(match)

        verify(callShotButton).checkShotButtonState(match)
        verify(trickShotButton).checkShotButtonState(match)
        verify(bounceShotButton).checkShotButtonState(match)
        verify(airShotButton).checkShotButtonState(match)
        verify(simpleShotButton).checkShotButtonState(match)

        verify(nextTurnButton).checkButtonState(match)
        verify(defenseFailedButton).checkButtonState(match)
        verify(failedButton).checkState(match)

        verifyNoMoreInteractions(playerButtonA,player2ButtonA,playerButtonB,player2ButtonB,drinkButtonA,drink2ButtonA,drink3ButtonA,drink4ButtonA,drink5ButtonB,drink6ButtonA,
            drinkButtonB,drink2ButtonB,drink3ButtonB,drink4ButtonB,drink5ButtonB,drink6ButtonB,callShotButton,trickShotButton,bounceShotButton,airShotButton,simpleShotButton,
            nextTurnButton,failedButton,defenseFailedButton)

    }

    private fun getPlayersA(): MutableList<PlayerButton> {
        playerButtonA = mock(PlayerButton::class.java)
        player2ButtonA = mock(PlayerButton::class.java)

        val playersButton: MutableList<PlayerButton> = arrayListOf()

        playersButton.add(playerButtonA)
        playersButton.add(player2ButtonA)
        return playersButton
    }

    private fun getPlayersB(): MutableList<PlayerButton> {
        playerButtonB = mock(PlayerButton::class.java)
        player2ButtonB = mock(PlayerButton::class.java)

        val playersButton: MutableList<PlayerButton> = arrayListOf()

        playersButton.add(playerButtonB)
        playersButton.add(player2ButtonB)
        return playersButton
    }

    private fun getDrinksA(): MutableList<DrinkButton> {
        drinkButtonA = mock(DrinkButton::class.java)
        drink2ButtonA = mock(DrinkButton::class.java)
        drink3ButtonA = mock(DrinkButton::class.java)
        drink4ButtonA = mock(DrinkButton::class.java)
        drink5ButtonA = mock(DrinkButton::class.java)
        drink6ButtonA = mock(DrinkButton::class.java)


        val drinksTeamA: MutableList<DrinkButton> = arrayListOf()
        drinksTeamA.add(drinkButtonA)
        drinksTeamA.add(drink2ButtonA)
        drinksTeamA.add(drink3ButtonA)
        drinksTeamA.add(drink4ButtonA)
        drinksTeamA.add(drink5ButtonA)
        drinksTeamA.add(drink6ButtonA)
        return drinksTeamA
    }

    private fun getDrinksB(): MutableList<DrinkButton> {
        drinkButtonB = mock(DrinkButton::class.java)
        drink2ButtonB = mock(DrinkButton::class.java)
        drink3ButtonB = mock(DrinkButton::class.java)
        drink4ButtonB = mock(DrinkButton::class.java)
        drink5ButtonB = mock(DrinkButton::class.java)
        drink6ButtonB = mock(DrinkButton::class.java)


        val drinksTeamB: MutableList<DrinkButton> = arrayListOf()
        drinksTeamB.add(drinkButtonB)
        drinksTeamB.add(drink2ButtonB)
        drinksTeamB.add(drink3ButtonB)
        drinksTeamB.add(drink4ButtonB)
        drinksTeamB.add(drink5ButtonB)
        drinksTeamB.add(drink6ButtonB)
        return drinksTeamB
    }

    private fun getShots(): MutableList<ShotButton> {
        callShotButton = mock(ShotButton::class.java)
        trickShotButton = mock(ShotButton::class.java)
        airShotButton = mock(ShotButton::class.java)
        bounceShotButton = mock(ShotButton::class.java)
        simpleShotButton = mock(ShotButton::class.java)

        val shotsButton: MutableList<ShotButton> = arrayListOf()
        shotsButton.add(callShotButton)
        shotsButton.add(trickShotButton)
        shotsButton.add(airShotButton)
        shotsButton.add(bounceShotButton)
        shotsButton.add(simpleShotButton)
        return shotsButton;
    }
}