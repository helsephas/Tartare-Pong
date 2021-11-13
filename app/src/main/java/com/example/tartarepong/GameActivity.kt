package com.example.tartarepong

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.game_activity.*
import models.Match
import models.NoDrinkSelectedException
import models.shots.ShotType
import ui.*

class GameActivity : Activity() {

    private var match: Match = Match().startGame()
    private var field: Field = Field()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_activity)

        setUp()
    }


    private fun setUp() {
        this.field.init(initPlayersButtonAList(), initPlayersButtonBList(), initDrinksButtonTeamA(), initDrinksButtonTeamB(), initShotsButton(), FailedButton(imageButtonFailed),DefenseFailedButton(buttonFailedDefense))
        this.field.checkFieldConfiguration(match)
        linkPlayersButtons()
        initListeners()
    }


    //region Visual

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
        drinksTeamA.add(DrinkButton(teamAdrink1, 1, 1))
        drinksTeamA.add(DrinkButton(teamAdrink2, 1, 2))
        drinksTeamA.add(DrinkButton(teamAdrink3, 1, 3))
        drinksTeamA.add(DrinkButton(teamAdrink4, 1, 4))
        drinksTeamA.add(DrinkButton(teamAdrink5, 1, 5))
        drinksTeamA.add(DrinkButton(teamAdrink6, 1, 6))
        return drinksTeamA
    }

    private fun initDrinksButtonTeamB(): MutableList<DrinkButton> {
        val drinksTeamB: MutableList<DrinkButton> = arrayListOf()
        drinksTeamB.add(DrinkButton(teamBdrink1, 1, 1))
        drinksTeamB.add(DrinkButton(teamBdrink2, 1, 2))
        drinksTeamB.add(DrinkButton(teamBdrink3, 1, 3))
        drinksTeamB.add(DrinkButton(teamBdrink4, 1, 4))
        drinksTeamB.add(DrinkButton(teamBdrink5, 1, 5))
        drinksTeamB.add(DrinkButton(teamBdrink6, 1, 6))
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


    //region Listeners

    private fun initListeners() {
        onValidateChoice()
        linkDrinksButtons()
        onFailedButton()
        linkShotTypeButtons()
        onFailedDefenserbutton()
        onChangeTeam()
    }

    private fun linkDrinksButtons() {
        initDrinksForTeamA()
        initDrinksForTeamB()
    }

    private fun initDrinksForTeamA() {
        teamAdrink1.setOnClickListener {
            onDrinkSelected(1)
        }
        teamAdrink2.setOnClickListener {
            onDrinkSelected(2)
        }
        teamAdrink3.setOnClickListener {
            onDrinkSelected(3)
        }
        teamAdrink4.setOnClickListener {
            onDrinkSelected(4)
        }
        teamAdrink5.setOnClickListener {
            onDrinkSelected(5)
        }
        teamAdrink6.setOnClickListener {
            onDrinkSelected(6)
        }
    }

    private fun initDrinksForTeamB() {
        teamBdrink1.setOnClickListener {
            onDrinkSelected(1)
        }
        teamBdrink2.setOnClickListener {
            onDrinkSelected(2)
        }
        teamBdrink3.setOnClickListener {
            onDrinkSelected(3)
        }
        teamBdrink4.setOnClickListener {
            onDrinkSelected(4)
        }
        teamBdrink5.setOnClickListener {
            onDrinkSelected(5)
        }
        teamBdrink6.setOnClickListener {
            onDrinkSelected(6)
        }
    }

    private fun onDrinkSelected(drinkNumber: Int) {
        match.currentDrinkSelected = match.getDrink(drinkNumber)
        field.checkFieldConfiguration(match)
    }

    private fun linkPlayersButtons() {
        buttonPlayer1.setOnClickListener {
            onPlayerSelected(1, 1)
        }
        buttonPlayer2.setOnClickListener {
            onPlayerSelected(1, 2)
        }
        buttonPlayer3.setOnClickListener {
            onPlayerSelected(2, 1)
        }
        buttonPlayer4.setOnClickListener {
            onPlayerSelected(2, 2)
        }
    }

    private fun onPlayerSelected(teamNumber: Int, playerNumber: Int) {
        if(teamNumber == match.getCurrentTeam().number){
            match.changePlayerCurrentPlayerTo(playerNumber)
        } else if(match.hasDefender() && teamNumber == match.getOtherTeam().number && match.defenderPlayer()!!.number == playerNumber){
            match.disabledDefender()
        }else if(match.defenderAvailable() && teamNumber == match.getOtherTeam().number) {
            match.changeDefenderPlayerTo(playerNumber)
        }

        field.checkFieldConfiguration(match)
    }

    private fun linkShotTypeButtons() {
        buttonTrickShot.setOnClickListener {
            onShotSelected(ShotType.TRICK_SHOT)
        }
        buttonAirShot.setOnClickListener {
            onShotSelected(ShotType.AIR_SHOT)
        }
        buttonBounceShot.setOnClickListener {
            onShotSelected(ShotType.BOUNCE)
        }
        buttonCallShot.setOnClickListener {
            onShotSelected(ShotType.CALL)
        }
        buttonSimpleShot.setOnClickListener {
            onShotSelected(ShotType.SIMPLE)
        }
    }

    private fun onShotSelected(shotType: ShotType) {
        match.currentShotType = shotType
        field.checkFieldConfiguration(match)
    }

    private fun onFailedButton() {
        imageButtonFailed.setOnClickListener {
            onFailedSelected()
        }
    }

    private fun onFailedSelected() {
        match.currentDrinkSelected = null
        field.checkFieldConfiguration(match)
    }

    private fun onFailedDefenserbutton(){
        buttonFailedDefense.setOnClickListener {
            onFailedDefenserbuttonSelected()
        }
    }

    private fun onFailedDefenserbuttonSelected(){
        match.hasFailedDefense =  !match.hasFailedDefense
        field.checkFieldConfiguration(match)
    }

    private fun onValidateChoice() {
        buttonValidate.setOnClickListener {
            onValidChoiceSelected()
        }
    }

    private fun onValidChoiceSelected() {
        if (isValidChoice()) {
            match.addShot()
            field.checkFieldConfiguration(match)
        }
    }

    private fun onChangeTeam(){
        buttonChangeTeam.setOnClickListener {
            onChangeTeamSelected()
        }
    }

    private fun onChangeTeamSelected() {
        match.changeTeam()
    }

    private fun isValidChoice(): Boolean {
        try {
            match.containsValidsChoice()
        } catch (e: NoDrinkSelectedException) {
            Toast.makeText(this, "Un verre doit être sélectionné pour shot réussi", Toast.LENGTH_LONG).show()
        }
        return true
    }

}
//endregion

/*f (match.hasDrinksToRemove()) {
        if (match.needToRemoveDrinks()) {
            Toast.makeText(this, "trois verres doivent être sélectionnés car deux fois même verre", Toast.LENGTH_LONG).show()
            return false
        }
    } else {
        if (isSuccess) {
            if (selectedDrink == null) {

                return false
            }
        } else {
            if (selectedDrink != null) {
                Toast.makeText(this, "Aucun verre ne doit être sélectionné pour shot raté", Toast.LENGTH_LONG).show()
                return false
            }
        }
    }*/


/*private fun onDrinkSelected(drinkNumber: Int) {
   match.currentDrinkSelected = match.getDrink(drinkNumber)*/
/* if (match.drinksDueToSameDrink != null) {
     if (!match.drinksDueToSameDrink!!.contains(drinkNumber)) {
         if (match.drinksDueToSameDrink!!.size == match.NUMBER_DRINKS_WHEN_DOUBLE) {
             match.drinksDueToSameDrink!!.removeAt(0)
             match.drinksDueToSameDrink!!.add(drinkNumber)
             //resetAllColorForTeams()
             for (drinkDueToSameDrink in match.drinksDueToSameDrink!!) {
                 if (match.currentPlayerPlaying.teamNumber == 1) {
                     //this.drinksTeamB[drinkDueToSameDrink - 1].setBackgroundResource(R.drawable.rounded_cirle_purple)
                 } else {
                     //this.drinksTeamA[drinkDueToSameDrink - 1].setBackgroundResource(R.drawable.rounded_cirle_purple)
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
     /*if (selectedDrink != null) {
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
     selectedDrink = drinkButton*/
 }*/

/*private fun scoredDrink(drinkButton: Button): Boolean {
    return drinksScored.contains(drinkButton)
}

private fun isFinished(): Boolean {
    return false
}*/

/*private fun drinkScored() {
    if (selectedDrink != null && !drinksScored.contains(selectedDrink!!)) {
        drinksScored.add(selectedDrink!!)
    }
    selectedDrink?.setBackgroundResource(R.drawable.rounded_cirle_purple)
}*/

/* private fun checkForImpactOfShot() {
    if (isSuccess) {
        match.nbShotSucced += 1
    }

    match.addShot(isSuccess)

    if (match.drinkNumberScored != null) {
        if (match.drinkNumberScored == match.currentDrinkSelected!!.number) {
            match.drinksDueToSameDrink = arrayListOf()
            //drinkNotDisplay()
        }
    }
    match.drinkNumberScored = match.currentDrinkSelected!!.number
    //drinkScored()*/

/*if (isValidChoice()) {
        if (match.drinksDueToSameDrink?.size == match.NUMBER_DRINKS_WHEN_DOUBLE) {

        } else {
            match.nbShots -= 1
            checkForImpactOfShot()
            changePlayer()
            if (match.nbShots == 0) {
                if (match.nbShotSucced != 2) {
                    changeTeaam()
                }
                match.nbShotSucced = 0
                match.nbShots = 2
            }
        }
    }*/