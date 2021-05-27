package com.example.tartarepong

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.game_activity.*
import models.Match
import models.Shot
import java.math.BigDecimal

class GameActivity : AppCompatActivity() {

    var match : Match = Match();
    var playerIdPlaying:BigDecimal = BigDecimal(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_activity)
        match.startGame()
    }

    fun onSuccessShotClick(){
        teamAdrink1.setOnClickListener {
            showFailedShotOptions()
        }
        teamAdrink2.setOnClickListener {
            showFailedShotOptions()
        }
        teamAdrink3.setOnClickListener {
            showFailedShotOptions()
        }
        teamAdrink4.setOnClickListener {
            showFailedShotOptions()
        }
        teamAdrink5.setOnClickListener {
            showFailedShotOptions()
        }
        teamAdrink6.setOnClickListener {
            showFailedShotOptions()
        }
    }

    fun onFailedShot(){

        imageButtonFailed.setOnClickListener {
            match.failedShot(playerIdPlaying)
        }
    }

    fun showFailedShotOptions(){

    }
}