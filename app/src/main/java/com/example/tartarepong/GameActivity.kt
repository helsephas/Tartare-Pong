package com.example.tartarepong

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.game_activity.*
import models.*
import java.math.BigDecimal

class GameActivity : Activity() {

    var match: Match = Match().startGame()

    var isSuccess: Boolean = false
    private var selectedButtonShot: Button? = null
    private var selectedDrink: Button? = null
    var drinksTeamA: MutableList<Button> = arrayListOf()
    var drinksTeamB: MutableList<Button> = arrayListOf()
    var drinksScored: MutableList<Button> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_activity)

        validateChoice()
        linkPlayersButtons()
        linkDrinksButtons()
        onFailedButton()
        linkShotTypeButtons()


        buttonPlayer3.isEnabled = !buttonPlayer3.isEnabled
        buttonPlayer4.isEnabled = !buttonPlayer4.isEnabled
        defineColorForButtonPlayer(buttonPlayer1)
        defineColorForButtonPlayer(buttonPlayer2)
        defineColorForButtonPlayer(buttonPlayer3)
        defineColorForButtonPlayer(buttonPlayer4)
        activateButtonShot(buttonSimpleShot)
        disableDrinksForTeam(1)
    }

    private fun linkShotTypeButtons() {
        buttonTrickShot.setOnClickListener {
            match.currentShopType = ShotType.TRICK_SHOT
            desactivateButtonShot()
            activateButtonShot(buttonTrickShot)
        }
        buttonAirShot.setOnClickListener {
            match.currentShopType = ShotType.AIR_SHOT
            desactivateButtonShot()
            activateButtonShot(buttonAirShot)
        }
        buttonBounceShot.setOnClickListener {
            match.currentShopType = ShotType.BOUNCE
            desactivateButtonShot()
            activateButtonShot(buttonBounceShot)
        }
        /*buttonCallShot.setOnClickListener {
            currentShopType = ShotType.CALL
            desactivateButtonShot()
            activateButtonShot(buttonCallShot)
        }*/
        buttonSimpleShot.setOnClickListener {
            match.currentShopType = ShotType.SIMPLE
            desactivateButtonShot()
            activateButtonShot(buttonSimpleShot)
        }
    }

    private fun desactivateButtonShot() {
        this.selectedButtonShot?.setBackgroundResource(R.drawable.rounded_cirle)
    }

    private fun activateButtonShot(button: Button) {
        button.setBackgroundResource(R.drawable.rounded_cirle_green)
        this.selectedButtonShot = button
    }


    private fun defineColorForButtonPlayer(playerButton: Button) {
        if (playerButton.isEnabled) {
            activatePlayerShot()
        } else {
            playerButton.setBackgroundColor(Color.RED)
        }
    }

    private fun activatePlayerShot() {
        var playerNumber: Int = match.currentPlayerPlaying.number
        var teamNumber: Int =match.currentPlayerPlaying.teamNumber
        if (teamNumber == 1) {
            if (playerNumber == 1) {
                buttonPlayer1.setBackgroundColor(Color.GREEN)
                if (!match.hasOneShot()) {
                    buttonPlayer2.setBackgroundColor(Color.BLUE)
                }
            } else {
                buttonPlayer2.setBackgroundColor(Color.GREEN)
                if (!match.hasOneShot()) {
                    buttonPlayer1.setBackgroundColor(Color.BLUE)
                }
            }
        } else {
            if (playerNumber == 1) {
                buttonPlayer3.setBackgroundColor(Color.GREEN)
                if (!match.hasOneShot()) {
                    buttonPlayer4.setBackgroundColor(Color.BLUE)
                }
            } else {
                buttonPlayer4.setBackgroundColor(Color.GREEN)
                if (!match.hasOneShot()) {
                    buttonPlayer3.setBackgroundColor(Color.BLUE)
                }
            }
        }
    }



    private fun desactivateOtherPlayerShot() {
        if (match.currentPlayerPlaying.teamNumber == 1) {
            if (match.currentPlayerPlaying.number == 1) {
                buttonPlayer1.isEnabled = !buttonPlayer1.isEnabled
                defineColorForButtonPlayer(buttonPlayer1)
            } else {
                buttonPlayer2.isEnabled = !buttonPlayer2.isEnabled
                defineColorForButtonPlayer(buttonPlayer2)
            }
        } else {
            if (match.currentPlayerPlaying.number == 1) {
                buttonPlayer3.isEnabled = !buttonPlayer3.isEnabled
                defineColorForButtonPlayer(buttonPlayer3)
            } else {
                buttonPlayer4.isEnabled = !buttonPlayer4.isEnabled
                defineColorForButtonPlayer(buttonPlayer4)
            }
        }
    }


    private fun linkPlayersButtons() {
        buttonPlayer1.setOnClickListener {
            onPlayerSelected(1, 1)
            defineColorForButtonPlayer(buttonPlayer1)
            defineColorForButtonPlayer(buttonPlayer2)
        }
        buttonPlayer2.setOnClickListener {
            onPlayerSelected(2, 1)
            defineColorForButtonPlayer(buttonPlayer1)
            defineColorForButtonPlayer(buttonPlayer2)
        }
        buttonPlayer3.setOnClickListener {
            onPlayerSelected(1, 2)
            defineColorForButtonPlayer(buttonPlayer3)
            defineColorForButtonPlayer(buttonPlayer4)
        }
        buttonPlayer4.setOnClickListener {
            onPlayerSelected(2, 2)
            defineColorForButtonPlayer(buttonPlayer3)
            defineColorForButtonPlayer(buttonPlayer4)
        }
    }

    private fun onPlayerSelected(playerNumber: Int, teamNumber: Int) {
        match.changePlayerCurrentPlayerTo(teamNumber,playerNumber)
    }

    private fun linkDrinksButtons() {
        initDrinksList()
        initDrinksForTeamA()
        initDrinksForTeamB()
    }

    private fun initDrinksForTeamA() {
        teamAdrink1.setOnClickListener {
            onDrinkSelected(1, teamAdrink1)
        }
        teamAdrink2.setOnClickListener {
            onDrinkSelected( 2, teamAdrink2)
        }
        teamAdrink3.setOnClickListener {
            onDrinkSelected( 3, teamAdrink3)
        }
        teamAdrink4.setOnClickListener {
            onDrinkSelected( 4, teamAdrink4)
        }
        teamAdrink5.setOnClickListener {
            onDrinkSelected( 5, teamAdrink5)
        }
        teamAdrink6.setOnClickListener {
            onDrinkSelected( 6, teamAdrink6)
        }
    }

    private fun initDrinksForTeamB() {
        teamBdrink1.setOnClickListener {
            onDrinkSelected( 1, teamBdrink1)
        }
        teamBdrink2.setOnClickListener {
            onDrinkSelected( 2, teamBdrink2)
        }
        teamBdrink3.setOnClickListener {
            onDrinkSelected( 3, teamBdrink3)
        }
        teamBdrink4.setOnClickListener {
            onDrinkSelected(4, teamBdrink4)
        }
        teamBdrink5.setOnClickListener {
            onDrinkSelected(5, teamBdrink5)
        }
        teamBdrink6.setOnClickListener {
            onDrinkSelected( 6, teamBdrink6)
        }
    }


    private fun initDrinksList() {
        this.drinksTeamA.add(teamAdrink1)
        this.drinksTeamA.add(teamAdrink2)
        this.drinksTeamA.add(teamAdrink3)
        this.drinksTeamA.add(teamAdrink4)
        this.drinksTeamA.add(teamAdrink5)
        this.drinksTeamA.add(teamAdrink6)

        this.drinksTeamB.add(teamBdrink1)
        this.drinksTeamB.add(teamBdrink2)
        this.drinksTeamB.add(teamBdrink3)
        this.drinksTeamB.add(teamBdrink4)
        this.drinksTeamB.add(teamBdrink5)
        this.drinksTeamB.add(teamBdrink6)
    }

    private fun onDrinkSelected(drinkNumber: Int, drinkButton: Button) {
        if (match.drinksDueToSameDrink != null) {
            if (!match.drinksDueToSameDrink!!.contains(drinkNumber)) {
                if (match.drinksDueToSameDrink!!.size == match.NUMBER_DRINKS_WHEN_DOUBLE) {
                    match.drinksDueToSameDrink!!.removeAt(0)
                    match.drinksDueToSameDrink!!.add(drinkNumber)
                    resetAllColorForTeams()
                    for (drinkDueToSameDrink in match.drinksDueToSameDrink!!) {
                        if (match.currentPlayerPlaying.teamNumber == 1) {
                            this.drinksTeamB[drinkDueToSameDrink - 1].setBackgroundResource(R.drawable.rounded_cirle_purple)
                        } else {
                            this.drinksTeamA[drinkDueToSameDrink - 1].setBackgroundResource(R.drawable.rounded_cirle_purple)
                        }
                    }
                }
                if (match.drinksDueToSameDrink!!.size != match.NUMBER_DRINKS_WHEN_DOUBLE) {
                    match.drinksDueToSameDrink!!.add(drinkNumber)
                    drinkButton.setBackgroundResource(R.drawable.rounded_cirle_purple)
                }
            }
        } else {
            match.changeDrinkSelected(drinkNumber)
            isSuccess = true
            drinkButton.setBackgroundResource(R.drawable.rounded_cirle_green)
            imageButtonFailed.setBackgroundResource(R.drawable.rounded_cirle)
            if (selectedDrink != null) {
                if (match.needToRemoveDrinks() && selectedDrink == drinkButton) {
                    changeDrawable(R.drawable.rounded_cirle_purple)
                }
                if (scoredDrink(selectedDrink!!)) {
                    changeDrawable(R.drawable.rounded_cirle_purple)
                }
                if (!scoredDrink(selectedDrink!!)) {
                    changeDrawable(R.drawable.rounded_cirle)
                }
            }
            selectedDrink = drinkButton
        }
    }

    private fun scoredDrink(drinkButton: Button): Boolean {
        return drinksScored.contains(drinkButton)
    }

    private fun onFailedButton() {
        imageButtonFailed.setOnClickListener {
            isSuccess = false
            imageButtonFailed.setBackgroundResource(R.drawable.rounded_cirle_green)
            selectedDrink?.setBackgroundResource(R.drawable.rounded_cirle)
        }
    }

    private fun validateChoice() {
        buttonValidate.setOnClickListener {
            if (isValidChoice()) {
                if (match.drinksDueToSameDrink?.size == match.NUMBER_DRINKS_WHEN_DOUBLE) {

                } else {
                    match.nbShots -= 1
                    checkForImpactOfShot()
                    changePlayer()
                    if (match.nbShots == 0) {
                        if (match.nbShotSucced != 2) {
                            match.changeTeam()
                        }
                        activateSelectionForTeam()
                        match.nbShotSucced = 0
                        match.nbShots = 2
                    }
                }
            }

        }
    }

    private fun isFinished(): Boolean {
        return false
    }

    private fun checkForImpactOfShot() {
        if(isSuccess){
            match.nbShotSucced += 1
        }

        match.addShot(isSuccess)

        if (match.drinkNumberScored != null) {
            if (match.drinkNumberScored == match.currentDrinkSelected!!.number) {
                match.drinksDueToSameDrink = arrayListOf()
                drinkNotDisplay()
            }
        }
        match.drinkNumberScored = match.currentDrinkSelected!!.number
        drinkScored()
    }

    private fun changePlayer() {
        desactivateOtherPlayerShot()
        match.changePlayer()
        activatePlayerShot()
    }

    private fun drinkNotDisplay() {
        selectedDrink?.alpha = 0.0F
        selectedDrink?.isClickable = false
        selectedDrink = null
    }

    private fun drinkScored() {
        if (selectedDrink != null && !drinksScored.contains(selectedDrink!!)) {
            drinksScored.add(selectedDrink!!)
        }
        selectedDrink?.setBackgroundResource(R.drawable.rounded_cirle_purple)
    }

    private fun isValidChoice(): Boolean {
        if (match.hasDrinksToRemove()) {
            if(match.needToRemoveDrinks()) {
                Toast.makeText(this, "trois verres doivent être sélectionnés car deux fois même verre", Toast.LENGTH_LONG).show()
                return false
            }
        } else {
            if (isSuccess) {
                if (selectedDrink == null) {
                    Toast.makeText(this, "Un verre doit être sélectionné pour shot réussi", Toast.LENGTH_LONG).show()
                    return false
                }
            } else {
                if (selectedDrink != null) {
                    Toast.makeText(this, "Aucun verre ne doit être sélectionné pour shot raté", Toast.LENGTH_LONG).show()
                    return false
                }
            }
        }
        return true
    }



    private fun activateSelectionForTeam() {
        if (match.currentPlayerPlaying.teamNumber == 1) {
            buttonPlayer1.isEnabled = true
            buttonPlayer2.isEnabled = true
            buttonPlayer3.isEnabled = false
            buttonPlayer4.isEnabled = false
            disableDrinksForTeam(1)
        } else {
            buttonPlayer1.isEnabled = false
            buttonPlayer2.isEnabled = false
            buttonPlayer3.isEnabled = true
            buttonPlayer4.isEnabled = true
            disableDrinksForTeam(2)
        }
        defineColorForButtonPlayer(buttonPlayer1)
        defineColorForButtonPlayer(buttonPlayer2)
        defineColorForButtonPlayer(buttonPlayer3)
        defineColorForButtonPlayer(buttonPlayer4)

    }

    private fun disableDrinksForTeam(number: Int) {
        if (number == 1) {
            for (teamADrink in this.drinksTeamA) {
                teamADrink.isClickable = false
            }
            for (teamBDrink in this.drinksTeamB) {
                teamBDrink.isClickable = true
            }
        } else {
            for (teamBDrink in this.drinksTeamB) {
                teamBDrink.isClickable = false
            }
            for (teamADrink in this.drinksTeamA) {
                teamADrink.isClickable = true
            }
        }
    }

    private fun resetAllColorForTeams() {
        if(match.currentPlayerPlaying.teamNumber == 1){
            for (teamBDrink in this.drinksTeamB) {
                teamBDrink.setBackgroundResource(R.drawable.rounded_cirle)
            }
        } else {
            for (teamADrink in this.drinksTeamA) {
                teamADrink.setBackgroundResource(R.drawable.rounded_cirle)
            }
        }
    }

    private fun changeDrawable(resource: Int) {
        selectedDrink?.setBackgroundResource(resource)
    }


}