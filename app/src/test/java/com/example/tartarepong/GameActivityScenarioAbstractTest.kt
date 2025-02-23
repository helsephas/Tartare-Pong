package com.example.tartarepong

import android.widget.Button
import android.widget.ImageButton
import models.Match
import models.shots.ShotType
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito
import ui.button.action.DefenseFailedButton
import ui.button.action.FailedButton
import ui.button.action.NextTurnButton
import ui.button.drink.DrinkButton
import ui.button.player.PlayerButton
import ui.button.shot.ShotButton
import ui.field.Field

abstract class GameActivityScenarioAbstractTest {

    protected var match: Match = Match().startGame()
    protected var field: Field = Field()
    protected lateinit var buttonPlayer1: Button
    protected lateinit var buttonPlayer2: Button
    protected lateinit var buttonPlayer3: Button
    protected lateinit var buttonPlayer4: Button

    protected lateinit var teamAdrink1: Button
    protected lateinit var teamAdrink2: Button
    protected lateinit var teamAdrink3: Button
    protected lateinit var teamAdrink4: Button
    protected lateinit var teamAdrink5: Button
    protected lateinit var teamAdrink6: Button

    protected lateinit var teamBdrink1: Button
    protected lateinit var teamBdrink2: Button
    protected lateinit var teamBdrink3: Button
    protected lateinit var teamBdrink4: Button
    protected lateinit var teamBdrink5: Button
    protected lateinit var teamBdrink6: Button

    protected lateinit var buttonSimpleShot: Button
    protected lateinit var buttonAirShot: Button
    protected lateinit var buttonTrickShot: Button
    protected lateinit var buttonCallShot: Button
    protected lateinit var buttonBounceShot: Button

    protected lateinit var buttonFailedDefense: Button
    protected lateinit var nextTurnButton: Button
    protected lateinit var imageButtonFailed: ImageButton

    @BeforeEach
    fun setUp() {
        init()
    }

    private fun init() {
        initMockButtons()
        this.field.init(
            initPlayersButtonAList(), initPlayersButtonBList(), initDrinksButtonTeamA(), initDrinksButtonTeamB(), initShotsButton(), FailedButton(imageButtonFailed),
            DefenseFailedButton(buttonFailedDefense), NextTurnButton(nextTurnButton)
        )
    }

    //region actions
    protected fun changeTeam(){
        match.changeTeam()
    }

    protected fun onShotSelected(shotType: ShotType) {
        match.changeShotType(shotType)
    }

    protected fun onFailedDefenserbuttonSelected() {
        match.revertDefenseFailed()
    }


    protected fun onDrinkSelected(drinkNumber: Int) {
        match.addDrink(drinkNumber)
    }

    protected fun onFailedSelected() {
        match.removeDrink()
    }

    protected fun addShot(){
        match.addShot()
    }

    protected fun onPlayerSelected(teamNumber: Int, playerNumber: Int) {
        when {
            match.isAttackTeam(teamNumber) -> {
                match.changePlayerCurrentPlayerTo(playerNumber)
            }
            match.isCurrentPlayerDefender(teamNumber,playerNumber) -> {
                match.disabledDefender()
            }
            match.isDefendableButNotDefended(teamNumber) -> {
                match.changeDefenderPlayerTo(playerNumber)
            }
        }
    }

    protected fun checkButtonState(button:Button,isAvailable:Boolean){
        Mockito.verify(button).isEnabled = isAvailable
    }

    protected fun checkButtonIsDisplayed(button:Button,isDisplayed:Boolean){
        if(isDisplayed){
            Mockito.verify(button).alpha = 1.0F
        } else {
            Mockito.verify(button).alpha = 0.0F
        }
    }

    //endregion

    //region Visual

    private fun initMockButtons(){
        buttonPlayer1 = Mockito.mock(Button::class.java)
        buttonPlayer2 = Mockito.mock(Button::class.java)
        buttonPlayer3 = Mockito.mock(Button::class.java)
        buttonPlayer4 = Mockito.mock(Button::class.java)

        teamAdrink1 = Mockito.mock(Button::class.java)
        teamAdrink2 = Mockito.mock(Button::class.java)
        teamAdrink3 = Mockito.mock(Button::class.java)
        teamAdrink4 = Mockito.mock(Button::class.java)
        teamAdrink5 = Mockito.mock(Button::class.java)
        teamAdrink5 = Mockito.mock(Button::class.java)
        teamAdrink6 = Mockito.mock(Button::class.java)

        teamBdrink1 = Mockito.mock(Button::class.java)
        teamBdrink2 = Mockito.mock(Button::class.java)
        teamBdrink3 = Mockito.mock(Button::class.java)
        teamBdrink4 = Mockito.mock(Button::class.java)
        teamBdrink5 = Mockito.mock(Button::class.java)
        teamBdrink5 = Mockito.mock(Button::class.java)
        teamBdrink6 = Mockito.mock(Button::class.java)

        buttonSimpleShot= Mockito.mock(Button::class.java)
        buttonAirShot= Mockito.mock(Button::class.java)
        buttonTrickShot= Mockito.mock(Button::class.java)
        buttonCallShot= Mockito.mock(Button::class.java)
        buttonBounceShot= Mockito.mock(Button::class.java)

        buttonFailedDefense= Mockito.mock(Button::class.java)
        nextTurnButton= Mockito.mock(Button::class.java)
        imageButtonFailed= Mockito.mock(ImageButton::class.java)
    }

    private fun initPlayersButtonAList(): MutableList<PlayerButton> {
        val playersButton: MutableList<PlayerButton> = arrayListOf()
        playersButton.add(PlayerButton(buttonPlayer1, 1))
        playersButton.add(PlayerButton(buttonPlayer2, 2))
        return playersButton
    }

    private fun initPlayersButtonBList(): MutableList<PlayerButton> {
        val playersButton: MutableList<PlayerButton> = arrayListOf()
        playersButton.add(PlayerButton(buttonPlayer3, 1))
        playersButton.add(PlayerButton(buttonPlayer4, 2))
        return playersButton
    }

    private fun initDrinksButtonTeamA(): MutableList<DrinkButton> {
        val drinksTeamA: MutableList<DrinkButton> = arrayListOf()
        drinksTeamA.add(DrinkButton(teamAdrink1, 1))
        drinksTeamA.add(DrinkButton(teamAdrink2, 2))
        drinksTeamA.add(DrinkButton(teamAdrink3, 3))
        drinksTeamA.add(DrinkButton(teamAdrink4, 4))
        drinksTeamA.add(DrinkButton(teamAdrink5, 5))
        drinksTeamA.add(DrinkButton(teamAdrink6, 6))
        return drinksTeamA
    }

    private fun initDrinksButtonTeamB(): MutableList<DrinkButton> {
        val drinksTeamB: MutableList<DrinkButton> = arrayListOf()
        drinksTeamB.add(DrinkButton(teamBdrink1, 1))
        drinksTeamB.add(DrinkButton(teamBdrink2, 2))
        drinksTeamB.add(DrinkButton(teamBdrink3, 3))
        drinksTeamB.add(DrinkButton(teamBdrink4, 4))
        drinksTeamB.add(DrinkButton(teamBdrink5, 5))
        drinksTeamB.add(DrinkButton(teamBdrink6, 6))
        return drinksTeamB
    }

    private fun initShotsButton(): MutableList<ShotButton> {
        val shotsButton: MutableList<ShotButton> = arrayListOf()
        shotsButton.add(ShotButton(buttonSimpleShot, ShotType.SIMPLE))
        shotsButton.add(ShotButton(buttonAirShot, ShotType.AIR_SHOT))
        shotsButton.add(ShotButton(buttonTrickShot, ShotType.TRICK_SHOT))
        shotsButton.add(ShotButton(buttonBounceShot, ShotType.BOUNCE))
        shotsButton.add(ShotButton(buttonCallShot, ShotType.CALL))
        return shotsButton
    }

    //endregion
}