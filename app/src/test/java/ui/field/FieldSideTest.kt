package ui.field

import models.Match
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import ui.button.drink.DrinkButton
import ui.button.player.PlayerButton

class FieldSideTest {

    private lateinit var playerButton: PlayerButton
    private lateinit var player2Button: PlayerButton

    private lateinit var match: Match
    private lateinit var fieldSide: FieldSide;

    private lateinit var drinkButton: DrinkButton
    private lateinit var drink2Button: DrinkButton
    private lateinit var drink3Button: DrinkButton
    private lateinit var drink4Button: DrinkButton
    private lateinit var drink5Button: DrinkButton
    private lateinit var drink6Button: DrinkButton

    @BeforeEach
    fun setUp() {
        this.match = Match().startGame()
        this.fieldSide = FieldSide(1, getPlayers(), getDrinks())
    }

    @Test
    fun testCheckOtherPlayersState() {
        this.fieldSide.checkAttackTeamPlayerState(match)
        verify(playerButton).checkAttackTeamPlayerState(match)
        verify(player2Button).checkAttackTeamPlayerState(match)
    }

    @Test
    fun testDesactivateDrinks() {
        this.fieldSide.checkAttackTeamDrinksState(match)
        verify(drinkButton).checkAttackTeamDrinkState(match)
        verify(drink2Button).checkAttackTeamDrinkState(match)
        verify(drink3Button).checkAttackTeamDrinkState(match)
        verify(drink4Button).checkAttackTeamDrinkState(match)
        verify(drink5Button).checkAttackTeamDrinkState(match)
        verify(drink6Button).checkAttackTeamDrinkState(match)
    }

    @Test
    fun testCheckPlayersState() {
        this.fieldSide.checkDefenserPlayersStates(match)
        verify(playerButton).checkDefenserPlayerState(match)
        verify(player2Button).checkDefenserPlayerState(match)
    }

    @Test
    fun testCheckDrinksState() {
        this.fieldSide.checkDefenseDrinksState(match)
        verify(drinkButton).checkDefenseDrinkState(match)
        verify(drink2Button).checkDefenseDrinkState(match)
        verify(drink3Button).checkDefenseDrinkState(match)
        verify(drink4Button).checkDefenseDrinkState(match)
        verify(drink5Button).checkDefenseDrinkState(match)
        verify(drink6Button).checkDefenseDrinkState(match)
    }

    private fun getPlayers(): MutableList<PlayerButton> {
        playerButton = mock(PlayerButton::class.java)
        player2Button = mock(PlayerButton::class.java)

        val playersButton: MutableList<PlayerButton> = arrayListOf()

        playersButton.add(playerButton)
        playersButton.add(player2Button)
        return playersButton
    }

    private fun getDrinks(): MutableList<DrinkButton> {
        drinkButton = mock(DrinkButton::class.java)
        drink2Button = mock(DrinkButton::class.java)
        drink3Button = mock(DrinkButton::class.java)
        drink4Button = mock(DrinkButton::class.java)
        drink5Button = mock(DrinkButton::class.java)
        drink6Button = mock(DrinkButton::class.java)


        val drinksTeamA: MutableList<DrinkButton> = arrayListOf()
        drinksTeamA.add(drinkButton)
        drinksTeamA.add(drink2Button)
        drinksTeamA.add(drink3Button)
        drinksTeamA.add(drink4Button)
        drinksTeamA.add(drink5Button)
        drinksTeamA.add(drink6Button)
        return drinksTeamA
    }

    @AfterEach
    fun verifyMockInteractions() {
        verifyNoMoreInteractions(playerButton, player2Button, drinkButton, drink2Button, drink3Button, drink4Button, drink5Button, drink6Button)
    }
}