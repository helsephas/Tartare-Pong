package com.example.tartarepong

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import com.example.tartarepong.databinding.GameActivityBinding
import models.Match
import models.shots.ShotType
import ui.button.action.DefenseFailedButton
import ui.button.action.FailedButton
import ui.button.action.NextTurnButton
import ui.button.drink.DrinkButton
import ui.button.player.PlayerButton
import ui.button.shot.ShotButton
import ui.field.Field

class GameActivity : Activity() {

    var match: Match = Match().startGame()
    var field: Field = Field()
    private lateinit var gameActivityBinding: GameActivityBinding

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameActivityBinding = GameActivityBinding.inflate(layoutInflater)
        val view = gameActivityBinding.root
        setContentView(view)
        setUp()
    }


    private fun setUp() {
        this.field.init(
            initPlayersButtonAList(), initPlayersButtonBList(), initDrinksButtonTeamA(), initDrinksButtonTeamB(),
            initShotsButton(), FailedButton(gameActivityBinding.imageButtonFailed), DefenseFailedButton(gameActivityBinding.buttonFailedDefense), NextTurnButton(gameActivityBinding.buttonChangeTeam)
        )
        this.field.checkFieldConfiguration(match)
        linkPlayersButtons()
        initListeners()
        gameActivityBinding.imageButtonFault.setOnClickListener {
            this.field.checkFieldConfiguration(match)
        }
    }


    //region Visual

    private fun initPlayersButtonAList(): MutableList<PlayerButton> {
        val playersButton: MutableList<PlayerButton> = arrayListOf()
        playersButton.add(PlayerButton(gameActivityBinding.buttonPlayer1, 1))
        playersButton.add(PlayerButton(gameActivityBinding.buttonPlayer2, 2))
        return playersButton
    }

    private fun initPlayersButtonBList(): MutableList<PlayerButton> {
        val playersButton: MutableList<PlayerButton> = arrayListOf()
        playersButton.add(PlayerButton(gameActivityBinding.buttonPlayer3, 1))
        playersButton.add(PlayerButton(gameActivityBinding.buttonPlayer4, 2))
        return playersButton
    }

    private fun initDrinksButtonTeamA(): MutableList<DrinkButton> {
        val drinksTeamA: MutableList<DrinkButton> = arrayListOf()
        drinksTeamA.add(DrinkButton( gameActivityBinding.teamAdrink1, 1))
        drinksTeamA.add(DrinkButton( gameActivityBinding.teamAdrink2, 2))
        drinksTeamA.add(DrinkButton( gameActivityBinding.teamAdrink3, 3))
        drinksTeamA.add(DrinkButton( gameActivityBinding.teamAdrink4, 4))
        drinksTeamA.add(DrinkButton( gameActivityBinding.teamAdrink5, 5))
        drinksTeamA.add(DrinkButton( gameActivityBinding.teamAdrink6, 6))
        return drinksTeamA
    }

    private fun initDrinksButtonTeamB(): MutableList<DrinkButton> {
        val drinksTeamB: MutableList<DrinkButton> = arrayListOf()
        drinksTeamB.add(DrinkButton(gameActivityBinding.teamBdrink1, 1))
        drinksTeamB.add(DrinkButton(gameActivityBinding.teamBdrink2, 2))
        drinksTeamB.add(DrinkButton(gameActivityBinding.teamBdrink3, 3))
        drinksTeamB.add(DrinkButton(gameActivityBinding.teamBdrink4, 4))
        drinksTeamB.add(DrinkButton(gameActivityBinding.teamBdrink5, 5))
        drinksTeamB.add(DrinkButton(gameActivityBinding.teamBdrink6, 6))
        return drinksTeamB
    }

    private fun initShotsButton(): MutableList<ShotButton> {
        val shotsButton: MutableList<ShotButton> = arrayListOf()
        shotsButton.add(ShotButton(gameActivityBinding.buttonSimpleShot, ShotType.SIMPLE))
        shotsButton.add(ShotButton(gameActivityBinding.buttonAirShot, ShotType.AIR_SHOT))
        shotsButton.add(ShotButton(gameActivityBinding.buttonTrickShot, ShotType.TRICK_SHOT))
        shotsButton.add(ShotButton(gameActivityBinding.buttonBounceShot, ShotType.BOUNCE))
        shotsButton.add(ShotButton(gameActivityBinding.buttonCallShot, ShotType.CALL))
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
         gameActivityBinding.teamAdrink1.setOnClickListener {
            onDrinkSelected(1,1)
        }
         gameActivityBinding.teamAdrink2.setOnClickListener {
            onDrinkSelected(1,2)
        }
         gameActivityBinding.teamAdrink3.setOnClickListener {
            onDrinkSelected(1,3)
        }
         gameActivityBinding.teamAdrink4.setOnClickListener {
            onDrinkSelected(1,4)
        }
         gameActivityBinding.teamAdrink5.setOnClickListener {
            onDrinkSelected(1,5)
        }
         gameActivityBinding.teamAdrink6.setOnClickListener {
            onDrinkSelected(1,6)
        }
    }

    private fun initDrinksForTeamB() {
        gameActivityBinding.teamBdrink1.setOnClickListener {
            onDrinkSelected(2,1)
        }
        gameActivityBinding.teamBdrink2.setOnClickListener {
            onDrinkSelected(2,2)
        }
        gameActivityBinding.teamBdrink3.setOnClickListener {
            onDrinkSelected(2,3)
        }
        gameActivityBinding.teamBdrink4.setOnClickListener {
            onDrinkSelected(2,4)
        }
        gameActivityBinding.teamBdrink5.setOnClickListener {
            onDrinkSelected(2,5)
        }
        gameActivityBinding.teamBdrink6.setOnClickListener {
            onDrinkSelected(2,6)
        }
    }

    private fun onDrinkSelected(teamNumber: Int,drinkNumber: Int) {
        if(match.hasImpactedShotToTreat(teamNumber)){
            match.selectDrinkForImpact(teamNumber,drinkNumber)
        } else {
            match.addDrink(drinkNumber)
        }
        field.checkFieldConfiguration(match)
    }

    private fun linkPlayersButtons() {
        gameActivityBinding.buttonPlayer1.setOnClickListener {
            onPlayerSelected(1, 1)
        }
        gameActivityBinding.buttonPlayer2.setOnClickListener {
            onPlayerSelected(1, 2)
        }
        gameActivityBinding.buttonPlayer3.setOnClickListener {
            onPlayerSelected(2, 1)
        }
        gameActivityBinding.buttonPlayer4.setOnClickListener {
            onPlayerSelected(2, 2)
        }
    }

    private fun onPlayerSelected(teamNumber: Int, playerNumber: Int) {
        when {
            match.isAttackTeam(teamNumber) -> {
                match.changePlayerCurrentPlayerTo(playerNumber)
            }
            match.isCurrentPlayerDefender(teamNumber, playerNumber) -> {
                match.disabledDefender()
            }
            match.isDefendableButNotDefended(teamNumber) -> {
                match.changeDefenderPlayerTo(playerNumber)
            }
        }

        field.checkFieldConfiguration(match)
    }

    private fun linkShotTypeButtons() {
        gameActivityBinding.buttonTrickShot.setOnClickListener {
            onShotSelected(ShotType.TRICK_SHOT)
        }
        gameActivityBinding.buttonAirShot.setOnClickListener {
            onShotSelected(ShotType.AIR_SHOT)
        }
        gameActivityBinding.buttonBounceShot.setOnClickListener {
            onShotSelected(ShotType.BOUNCE)
        }
        gameActivityBinding.buttonCallShot.setOnClickListener {
            onShotSelected(ShotType.CALL)
        }
        gameActivityBinding.buttonSimpleShot.setOnClickListener {
            onShotSelected(ShotType.SIMPLE)
        }
    }

    private fun onShotSelected(shotType: ShotType) {
        match.changeShotType(shotType)
        field.checkFieldConfiguration(match)
    }

    private fun onFailedButton() {
        gameActivityBinding.imageButtonFailed.setOnClickListener {
            onFailedSelected()
        }
    }

    private fun onFailedSelected() {
        match.removeDrink()
        field.checkFieldConfiguration(match)
    }

    private fun onFailedDefenserbutton() {
        gameActivityBinding.buttonFailedDefense.setOnClickListener {
            onFailedDefenserbuttonSelected()
        }
    }

    private fun onFailedDefenserbuttonSelected() {
        match.revertDefenseFailed()
        field.checkFieldConfiguration(match)
    }

    private fun onValidateChoice() {
        gameActivityBinding.buttonValidate.setOnClickListener {
            onValidChoiceSelected()
        }
    }

    private fun onValidChoiceSelected() {
        if(match.hasImpactedShotToTreat()){
            showThatDrinksNeeded()
        } else{
            match.addShot()
        }
        field.checkFieldConfiguration(match)
    }

    private fun showThatDrinksNeeded(){
        Toast.makeText(this, "Il faut résoudre les impacts avant", Toast.LENGTH_LONG).show()
    }

    private fun onChangeTeam() {
        gameActivityBinding.buttonChangeTeam.setOnClickListener {
            onChangeTeamSelected()
        }
    }

    private fun onChangeTeamSelected() {
        match.changeTeam()
        field.checkFieldConfiguration(match)
    }

}
//endregion