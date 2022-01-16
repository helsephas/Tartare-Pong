package com.example.tartarepong

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.game_activity.*
import models.Match
import models.exception.NoDrinkSelectedException
import models.shots.ShotType
import ui.*

class GameActivity : Activity() {

    var match: Match = Match().startGame()
    var field: Field = Field()

    public override fun onCreate(savedInstanceState: Bundle?) {
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
        drinksTeamA.add(DrinkButton(teamAdrink1 , 1))
        drinksTeamA.add(DrinkButton(teamAdrink2, 2))
        drinksTeamA.add(DrinkButton(teamAdrink3, 3))
        drinksTeamA.add(DrinkButton(teamAdrink4, 4))
        drinksTeamA.add(DrinkButton(teamAdrink5, 5))
        drinksTeamA.add(DrinkButton(teamAdrink6, 6))
        return drinksTeamA
    }

    private fun initDrinksButtonTeamB(): MutableList<DrinkButton> {
        val drinksTeamB: MutableList<DrinkButton> = arrayListOf()
        drinksTeamB.add(DrinkButton(teamBdrink1,  1))
        drinksTeamB.add(DrinkButton(teamBdrink2,  2))
        drinksTeamB.add(DrinkButton(teamBdrink3,  3))
        drinksTeamB.add(DrinkButton(teamBdrink4,  4))
        drinksTeamB.add(DrinkButton(teamBdrink5,  5))
        drinksTeamB.add(DrinkButton(teamBdrink6,  6))
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
        match.addDrink(drinkNumber)
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
        match.removeDrink()
        field.checkFieldConfiguration(match)
    }

    private fun onFailedDefenserbutton(){
        buttonFailedDefense.setOnClickListener {
            onFailedDefenserbuttonSelected()
        }
    }

    private fun onFailedDefenserbuttonSelected(){
        match.revertDefenseFailed()
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