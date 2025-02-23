package models.shots

import models.Match
import models.Team

class ShotImpact(match: Match) {

    var hasToReplay:Boolean = false
    private var totalAttackImpact:Int = 0
    private var totalDefendImpact:Int = 0
    private var lastShot = match.getLastShot()
    private var firstShot = match.getOtherShotOfThisTurn()
    private var successShot : Shot? = null
    private var attackingTeam : Team = match.getAttackTeam()
    private var defendingTeam : Team = match.getDefenseTeam()
    private var isTwoShotsOnTheSameDrinks : Boolean = match.isTwoShotsOnSameDrinksScored()

    fun impactShot():Boolean{
        if(lastShot.isSuccess && firstShot.isSuccess){
            hasToReplay = true
            totalAttackImpact = firstShot.shotImpact + firstShot.shotImpact
            totalAttackImpact += 1
            if(isTwoShotsOnTheSameDrinks){
                totalAttackImpact += 1
            }
        } else {
            determineIfSucceedShot()
            if(hasSucceedShot()){
                totalAttackImpact = successShot!!.shotImpact
            }
            if(isAirShot(firstShot)){
                totalDefendImpact += 1
            }
            if(isAirShot(lastShot)){
                totalDefendImpact += 1
            }
        }
        if(totalDefendImpact != 0) {
            defendingTeam.drinksTeamImpacted = mutableListOf(totalDefendImpact - 1,0)
        }
        if(totalAttackImpact != 0) {
            attackingTeam.drinksTeamImpacted = mutableListOf(totalAttackImpact - 1,0)
        }

        return totalAttackImpact != 0 || totalDefendImpact != 0
    }


    private fun determineIfSucceedShot(){
        if(lastShot.isSuccess){
            successShot = lastShot
        } else {
            if(firstShot.isSuccess){
                successShot = firstShot
            }
        }
    }

    private fun isAirShot(shot: Shot):Boolean{
        return shot.shotType.isAirShot() && (shot as AirShot).hasBeenDefended()
    }

    private fun hasSucceedShot(): Boolean {
        return successShot != null
    }

    //NE PAS OUBLIER AVEC TRICK SHOT RATE ET REUSSI


    //SIMPLE SI 0 REUSSI alors rien UN REUSSI alors -1 verre SI DEUX reussi alors -2 et ça rejoue et si les deux dans le même verre -3 + rejoue SELECTION 0/0/1
    //AIR SHOT SI DEFENDU alors - 1 pour ATTACK 2 AIR SHOT -2 SELECTION 1/2
    //CALL REUSSI -2 2 CALL -4 + REJOUE SELECTION 1/2/3???
    //SIMPLE REUSSI CALL REUSSI -3 REJOUE
    //BOUNCE REUSSI

    /*if (isSuccess()) {
        if(currentDrinkSelected!!.isDone){
            hasDoubleShot = true
            nbShots = 2
            currentPlayerPlaying = getCurrentTeam().getPlayerByNumber(1)
        } else {
            currentDrinkSelected!!.isDone = true
        }
        getCurrentTeam().redemptionCount -= this.getLastShot().shotImpact
    }*/
}