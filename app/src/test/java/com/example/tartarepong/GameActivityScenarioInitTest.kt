package com.example.tartarepong

import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify

class GameActivityScenarioInitTest : GameActivityScenarioAbstractTest() {

    @Test
    fun testInit(){
        this.field.checkFieldConfiguration(match)
        //PLAYER 1 ACTIVATED PLAYER 2 AVAILALBLE
        //PLAYER 3 - 4 NOT AVAILABLE
        checkButtonState(buttonPlayer1,false)
        checkButtonState(buttonPlayer2,true)
        checkButtonState(buttonPlayer3,false)
        checkButtonState(buttonPlayer4,false)

        //SIMPLE SHOT/BOUNCE/AIR SHOT ACTIVATED BY DEFAULT3
        checkButtonState(buttonSimpleShot,false)
        checkButtonState(buttonTrickShot,false)
        checkButtonState(buttonCallShot,false)
        checkButtonState(buttonBounceShot,true)
        checkButtonState(buttonAirShot,true)

        //DRINKS OF ATTACK DISABLED
        checkButtonState(teamAdrink1,false)
        checkButtonState(teamAdrink2,false)
        checkButtonState(teamAdrink3,false)
        checkButtonState(teamAdrink4,false)
        checkButtonState(teamAdrink5,false)
        checkButtonState(teamAdrink6,false)

        //DRINKS OF DEFENSE SELECTABLE
        checkButtonState(teamBdrink1,true)
        checkButtonState(teamBdrink2,true)
        checkButtonState(teamBdrink3,true)
        checkButtonState(teamBdrink4,true)
        checkButtonState(teamBdrink5,true)
        checkButtonState(teamBdrink6,true)

        //CHECK FAILED STATE
        checkButtonIsDisplayed(buttonFailedDefense,false)
        verify(imageButtonFailed).isEnabled = false
    }


}