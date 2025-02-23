package com.example.tartarepong

import android.graphics.Color
import models.shots.ShotType
import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify

class GameActivityScenarioTest : GameActivityScenarioAbstractTest() {
    @Test
    fun testWhenAirShotSelected(){
        onShotSelected(ShotType.AIR_SHOT)
        field.checkFieldConfiguration(match)

        checkButtonState(buttonSimpleShot,true)
        checkButtonState(buttonBounceShot,true)
        checkButtonState(buttonAirShot,false)

        //PLAYER 1 ACTIVATED PLAYER 2 AVAILALBLE
        //PLAYER 3 - 4 DEFENSERS
        checkButtonState(buttonPlayer1,false)
        checkButtonState(buttonPlayer2,true)
        checkButtonState(buttonPlayer3,true)
        checkButtonState(buttonPlayer4,true)
    }

    @Test
    fun testWhenBounceShotSelected(){
        onShotSelected(ShotType.BOUNCE)
        field.checkFieldConfiguration(match)

        checkButtonState(buttonSimpleShot,true)
        checkButtonState(buttonBounceShot,false)
        checkButtonState(buttonAirShot,true)

        //PLAYER 1 ACTIVATED PLAYER 2 AVAILALBLE
        //PLAYER 3 - 4 DEFENSERS
        checkButtonState(buttonPlayer1,false)
        checkButtonState(buttonPlayer2,true)
        checkButtonState(buttonPlayer3,true)
        checkButtonState(buttonPlayer4,true)
    }

    @Test
    fun testWhenOneDrinkSelected(){
        onDrinkSelected(1)
        field.checkFieldConfiguration(match)

        checkButtonState(teamBdrink1,false)
        checkButtonState(teamBdrink2,true)
        verify(imageButtonFailed).isEnabled = true
    }

    @Test
    fun testWhenOneDrinkSelectedThenFailed(){
        onDrinkSelected(1)
        onFailedSelected()
        field.checkFieldConfiguration(match)

        checkButtonState(teamBdrink1,true)
        checkButtonState(teamBdrink2,true)
        verify(imageButtonFailed).isEnabled = false
    }

    @Test
    fun testWhenOneDrinkSelectedThenOtherShotSelected(){
        onDrinkSelected(1)
        onShotSelected(ShotType.BOUNCE)

        field.checkFieldConfiguration(match)

        checkButtonState(teamBdrink1,false)
        checkButtonState(teamBdrink2,true)
        verify(imageButtonFailed).isEnabled = true
    }

    @Test
    fun testWhenOneDrinkSelectedThenAirShotSelected(){
        onDrinkSelected(1)
        onShotSelected(ShotType.AIR_SHOT)

        field.checkFieldConfiguration(match)

        checkButtonState(teamBdrink1,false)
        checkButtonState(teamBdrink2,false)
        verify(imageButtonFailed).isEnabled = false
    }

    @Test
    fun testWhenOtherPlayerSelected(){
        onPlayerSelected(1,2)
        field.checkFieldConfiguration(match)
        //PLAYER 2 ACTIVATED PLAYER 1 AVAILALBLE
        //PLAYER 3 - 4 NOT AVAILABLE
        checkButtonState(buttonPlayer1,true)
        checkButtonState(buttonPlayer2,false)
        checkButtonState(buttonPlayer3,false)
        checkButtonState(buttonPlayer4,false)

        checkButtonState(buttonSimpleShot,false)
        checkButtonState(buttonTrickShot,false)
        checkButtonState(buttonCallShot,false)
        checkButtonState(buttonBounceShot,true)
        checkButtonState(buttonAirShot,true)
    }

    @Test
    fun testWhenDefenderSelectedDefenseFailedAvailable(){
        onShotSelected(ShotType.BOUNCE)
        onDrinkSelected(1)
        onPlayerSelected(2,1)
        field.checkFieldConfiguration(match)
        //PLAYER 1 ACTIVATED PLAYER 2 AVAILALBLE

        checkButtonState(buttonPlayer3,true)
        checkButtonState(buttonPlayer4,true)

        verify(imageButtonFailed).isEnabled = true
        checkButtonIsDisplayed(buttonFailedDefense,true)
    }

    @Test
    fun testWhenDefenderSelectedDefenseFailedSelected(){
        onShotSelected(ShotType.BOUNCE)
        onDrinkSelected(1)
        onPlayerSelected(2,1)
        onFailedDefenserbuttonSelected()
        field.checkFieldConfiguration(match)

        verify(imageButtonFailed).isEnabled = true
        checkButtonIsDisplayed(buttonFailedDefense,true)
        verify(buttonFailedDefense).setBackgroundResource(R.drawable.rounded_cirle_green)
    }

    @Test
    fun testWhenDefenderSelectedDefenseFailedSelectedThenSelectedAgain(){
        onShotSelected(ShotType.BOUNCE)
        onDrinkSelected(1)
        onPlayerSelected(2,1)
        onFailedDefenserbuttonSelected()
        onFailedDefenserbuttonSelected()
        field.checkFieldConfiguration(match)

        verify(imageButtonFailed).isEnabled = true
        checkButtonIsDisplayed(buttonFailedDefense,true)
        verify(buttonFailedDefense).setBackgroundResource(R.drawable.rounded_cirle)
    }

    @Test
    fun testWhenDefenderSelectedDefenseFailedSelectedThenDefenderPlayerRemoveThenReselectedShouldNotShowFailedDefense(){
        onShotSelected(ShotType.BOUNCE)
        onDrinkSelected(1)
        onPlayerSelected(2,1)
        onFailedDefenserbuttonSelected()
        onPlayerSelected(2,1)
        onPlayerSelected(2,1)
        field.checkFieldConfiguration(match)

        verify(imageButtonFailed).isEnabled = true
        checkButtonIsDisplayed(buttonFailedDefense,true)
        verify(buttonFailedDefense).setBackgroundResource(R.drawable.rounded_cirle)
    }

    @Test
    fun testWhenDefenderSelectedDefenseFailedSelectedThenOtherShotTypeShouldRemoveDefending(){
        onShotSelected(ShotType.BOUNCE)
        onDrinkSelected(1)
        onPlayerSelected(2,1)
        onFailedDefenserbuttonSelected()
        onShotSelected(ShotType.SIMPLE)
        onShotSelected(ShotType.BOUNCE)
        field.checkFieldConfiguration(match)

        verify(imageButtonFailed).isEnabled = true
        checkButtonIsDisplayed(buttonFailedDefense,false)
        verify(buttonPlayer3).setBackgroundColor(Color.CYAN)
    }

    @Test
    fun testWhenFirstPlayerHasFailed(){
        onFailedSelected()
        addShot()
        onPlayerSelected(1,1)

        field.checkFieldConfiguration(match)

        checkButtonState(buttonPlayer1,false)
        checkButtonState(buttonPlayer2,true)
        checkButtonState(buttonPlayer3,false)
        checkButtonState(buttonPlayer4,false)

        verify(imageButtonFailed).isEnabled = false
    }

    @Test
    fun testWhenFirstPlayerHasFailedTrickShot(){
        onFailedSelected()
        addShot()
        onPlayerSelected(1,1)
        addShot()
        field.checkFieldConfiguration(match)

        checkButtonState(buttonPlayer1,false)
        checkButtonState(buttonPlayer2,false)
        checkButtonState(buttonPlayer3,false)
        checkButtonState(buttonPlayer4,false)
    }

    @Test
    fun testWhenFirstPlayerHasFailedTrickShotAndSecondPlayerHasShotAvailable(){
        onFailedSelected()
        addShot()
        onPlayerSelected(1,1)
        addShot()
        field.checkFieldConfiguration(match)

        checkButtonState(buttonPlayer1,false)
        verify(buttonPlayer1).setBackgroundColor(Color.RED)
        checkButtonState(buttonPlayer2,false)
        verify(buttonPlayer2).setBackgroundColor(Color.GREEN)
        checkButtonState(buttonPlayer3,false)
        checkButtonState(buttonPlayer4,false)
        checkButtonIsDisplayed(nextTurnButton,false)
    }

    @Test
    fun testWhenFirstPlayerHasSuccessAndSecondPlayerFailed(){
        onDrinkSelected(1)
        addShot()
        onFailedSelected()
        addShot()
        field.checkFieldConfiguration(match)

        checkButtonState(buttonPlayer1,false)
        verify(buttonPlayer1).setBackgroundColor(Color.RED)
        checkButtonState(buttonPlayer2,false)
        verify(buttonPlayer2).setBackgroundColor(Color.GREEN)
        checkButtonState(buttonPlayer3,false)
        checkButtonState(buttonPlayer4,false)
        checkButtonIsDisplayed(nextTurnButton,true)
    }

    @Test
    fun testWhenFirstPlayerHasFailedTrickShotAndSecondPlayerHasFailedShot(){
        onFailedSelected()
        addShot()
        onPlayerSelected(1,1)
        addShot()
        addShot()
        field.checkFieldConfiguration(match)

        checkButtonState(buttonPlayer1,false)
        verify(buttonPlayer1).setBackgroundColor(Color.RED)
        checkButtonState(buttonPlayer2,false)
        verify(buttonPlayer2).setBackgroundColor(Color.GREEN)
        checkButtonState(buttonPlayer3,false)
        checkButtonState(buttonPlayer4,false)

    }

    @Test
    fun testNextTurnShouldAppearOnlyWhen2PlayersHasPlayed(){
        onFailedSelected()
        addShot()
        onPlayerSelected(1,1)
        addShot()
        addShot()
        field.checkFieldConfiguration(match)
        checkButtonIsDisplayed(nextTurnButton,true)
    }

    @Test
    fun testNextTurn(){
        onFailedSelected()
        addShot()
        onPlayerSelected(1,1)
        addShot()
        addShot()
        changeTeam()
        field.checkFieldConfiguration(match)
        checkButtonIsDisplayed(nextTurnButton,false)

        checkButtonState(buttonPlayer1,false)
        verify(buttonPlayer1).setBackgroundColor(Color.RED)
        checkButtonState(buttonPlayer2,false)
        verify(buttonPlayer2).setBackgroundColor(Color.RED)
        checkButtonState(buttonPlayer3,false)
        verify(buttonPlayer3).setBackgroundColor(Color.GREEN)
        checkButtonState(buttonPlayer4,true)
        verify(buttonPlayer4).setBackgroundColor(Color.BLACK)
        verify(buttonSimpleShot).setBackgroundResource(R.drawable.rounded_cirle_green)
        verify(buttonTrickShot).setBackgroundResource(R.drawable.rounded_cirle_red)
    }

    @Test
    fun testNextTurnAfterAirShot(){
        onFailedSelected()
        addShot()
        match.changeShotType(ShotType.AIR_SHOT)
        addShot()
        field.checkFieldConfiguration(match)
        checkButtonIsDisplayed(nextTurnButton,false)

        checkButtonState(buttonPlayer1,false)
        verify(buttonPlayer1).setBackgroundColor(Color.RED)
        checkButtonState(buttonPlayer2,false)
        verify(buttonPlayer2).setBackgroundColor(Color.RED)
        checkButtonState(buttonPlayer3,false)
        verify(buttonPlayer3).setBackgroundColor(Color.GREEN)
        checkButtonState(buttonPlayer4,true)
        verify(buttonPlayer4).setBackgroundColor(Color.BLACK)
        verify(buttonSimpleShot).setBackgroundResource(R.drawable.rounded_cirle_green)
        verify(buttonTrickShot).setBackgroundResource(R.drawable.rounded_cirle_red)
    }


}