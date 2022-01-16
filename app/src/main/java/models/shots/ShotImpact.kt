package models.shots

class ShotImpact(var lastShot: Shot) {
    var drinksImpacted: MutableList<Int> = arrayListOf()

    fun impactShot():Boolean{
        if(lastShot.shotType.isAirShot() && (lastShot as AirShot).hasBeenDefended()){
            return drinksImpacted.size == lastShot.shotImpact
        } else {
            return drinksImpacted.size == lastShot.shotImpact - 1
        }
    }


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