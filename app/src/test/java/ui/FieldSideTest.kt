package ui

import junit.framework.TestCase
import models.Match
import org.mockito.Mockito.*

class FieldSideTest : TestCase() {
    
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
    
    public override fun setUp() {
        super.setUp()
        this.match = Match().startGame()
        this.fieldSide = FieldSide(1,getPlayers(),getDrinks())
    }

    fun testCheckOtherPlayersState() {
        this.fieldSide.checkAttackTeamPlayerState(match)
        verify(playerButton).checkAttackTeamPlayerState(match)
        verify(player2Button).checkAttackTeamPlayerState(match)
    }

    fun testDesactivateDrinks() {
        this.fieldSide.checkAttackTeamDrinksState(match)
        verify(drinkButton).checkAttackTeamDrinkState(match)
        verify(drink2Button).checkAttackTeamDrinkState(match)
        verify(drink3Button).checkAttackTeamDrinkState(match)
        verify(drink4Button).checkAttackTeamDrinkState(match)
        verify(drink5Button).checkAttackTeamDrinkState(match)
        verify(drink6Button).checkAttackTeamDrinkState(match)
    }

    fun testCheckPlayersState() {
        this.fieldSide.checkDefenserPlayersStates(match)
        verify(playerButton).checkDefenserPlayerState(match)
        verify(player2Button).checkDefenserPlayerState(match)
    }

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
        player2Button =  mock(PlayerButton::class.java)

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
        drinksTeamA.add(drinkButton )
        drinksTeamA.add(drink2Button)
        drinksTeamA.add(drink3Button)
        drinksTeamA.add(drink4Button)
        drinksTeamA.add(drink5Button)
        drinksTeamA.add(drink6Button)
        return drinksTeamA
    }
}