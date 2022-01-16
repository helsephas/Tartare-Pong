package models


import models.shots.ShotType
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MatchTest {
    
    private lateinit var match:Match

    @Before
    fun setUp() {
        this.match = Match().startGame()
    }
    
    @Test
    fun testStartGame(){
        assertNotNull(match)
        assertEquals(match.teams.size,2)
        assertEquals(match.teams[0].players[0],match.currentPlayerPlaying)
    }

    @Test
    fun addShotToMatchSimpleSuccess(){
        match.currentShotType = ShotType.SIMPLE
        match.addDrink(1)
        match.addShot()
        assertEquals(match.shots.size,1)
        assertTrue(match.isDrinkScored(1))
        assertFalse(match.isDrinkScoredDefinitivelyScoredForCurrentTeam(1))
        assertFalse(match.isDrinkScoredDefinitivelyScoredForCurrentTeam(2))
        assertFalse(match.isDrinkScoredDefinitivelyScoredForOtherTeam(1))
        assertFalse(match.isDrinkScoredDefinitivelyScoredForOtherTeam(2))
        assertFalse(match.getAttackTeam().getPlayerByNumber(1).trickShotAvailable)
    }

    @Test
    fun addShotToMatchSimpleFailed(){
        match.currentShotType = ShotType.SIMPLE
        match.addShot()
        assertEquals(match.shots.size,1)
        assertFalse(match.isDrinkScored(1))
        assertFalse(match.isDrinkScoredDefinitivelyScoredForCurrentTeam(1))
        assertFalse(match.isDrinkScoredDefinitivelyScoredForOtherTeam(1))
        assertEquals(match.currentPlayerPlaying,match.teams[0].getPlayerByNumber(2))
        assertTrue(match.getAttackTeam().getPlayerByNumber(1).trickShotAvailable)
    }

    @Test
    fun testCurrentPlayerHasAlreadyPlayed(){
        match.currentShotType = ShotType.SIMPLE
        match.addDrink(1)
        match.addShot()
        match.currentPlayerPlaying = match.getAttackTeam().getPlayerByNumber(1)
        assertTrue(match.currentPlayerHasAlreadyPlayed())
        assertFalse(match.hasCurrentPlayerHasTrickShot())
    }

    @Test
    fun testCurrentPlayerHasNotAlreadyPlayed(){
        match.currentShotType = ShotType.SIMPLE
        match.addShot()
        match.currentPlayerPlaying = match.getAttackTeam().getPlayerByNumber(1)
        assertFalse(match.currentPlayerHasAlreadyPlayed())
        assertTrue(match.hasCurrentPlayerHasTrickShot())
    }

    @Test
    fun testChangeDefenderPlayerTo(){

    }

    @Test
    fun testGetCurrentTeam(){
        val team:Team = match.getAttackTeam()
        assertEquals(team,match.teams[0])
    }



    @Test
    fun testGetOtherTeam(){
        val team:Team = match.getDefenseTeam()
        assertEquals(team,match.teams[1])
    }

    @Test
    fun testIsDrinkSelected(){
        assertFalse(match.isDrinkSelected())
        match.addDrink(1)
        assertTrue(match.isDrinkSelected())
        match.removeDrink()
        assertFalse(match.isDrinkSelected())
    }

    @Test
    fun testIsDrinkNumberSelected(){
        assertFalse(match.isDrinkNumberSelected(1))
        match.addDrink(1)
        assertFalse(match.isDrinkNumberSelected(2))
        assertTrue(match.isDrinkNumberSelected(1))
    }

    @Test
    fun testDrinks1Callable(){
        assertFalse(match.currentDrinkIsCallable())

        match.addDrink(1)
        assertFalse(match.currentDrinkIsCallable())

        match.getDefenseTeam().getDrinkByNumber(2).isDone = true
        match.getDefenseTeam().getDrinkByNumber(3).isDone = true
        assertTrue(match.currentDrinkIsCallable())

        match.getDefenseTeam().getDrinkByNumber(3).isDone = false
        assertFalse(match.currentDrinkIsCallable())

        match.getDefenseTeam().getDrinkByNumber(3).isDone = true
        match.getDefenseTeam().getDrinkByNumber(2).isDone = false
        assertFalse(match.currentDrinkIsCallable())

        match.getDefenseTeam().getDrinkByNumber(2).isDone = true
        match.getDefenseTeam().getDrinkByNumber(3).isDone = true
        match.getDefenseTeam().getDrinkByNumber(4).isDone = true
        assertTrue(match.currentDrinkIsCallable())

        match.getDefenseTeam().getDrinkByNumber(4).isDone = false
        match.getDefenseTeam().getDrinkByNumber(6).isDone = true
        assertTrue(match.currentDrinkIsCallable())

        match.getDefenseTeam().getDrinkByNumber(5).isDone = true
        assertFalse(match.currentDrinkIsCallable())
    }

    @Test
    fun testDrinks4Callable(){
        assertFalse(match.currentDrinkIsCallable())

        match.addDrink(4)
        assertFalse(match.currentDrinkIsCallable())

        match.getDefenseTeam().getDrinkByNumber(2).isDone = true
        match.getDefenseTeam().getDrinkByNumber(5).isDone = true
        assertTrue(match.currentDrinkIsCallable())

        match.getDefenseTeam().getDrinkByNumber(3).isDone = true
        assertFalse(match.currentDrinkIsCallable())

        match.getDefenseTeam().getDrinkByNumber(3).isDone = false
        match.getDefenseTeam().getDrinkByNumber(6).isDone = true
        assertTrue(match.currentDrinkIsCallable())

        match.getDefenseTeam().getDrinkByNumber(6).isDone = false
        match.getDefenseTeam().getDrinkByNumber(1).isDone = true
        assertTrue(match.currentDrinkIsCallable())
    }

    @Test
    fun testDrinks6Callable(){
        assertFalse(match.currentDrinkIsCallable())

        match.addDrink(6)
        assertFalse(match.currentDrinkIsCallable())

        match.getDefenseTeam().getDrinkByNumber(3).isDone = true
        match.getDefenseTeam().getDrinkByNumber(5).isDone = true
        assertTrue(match.currentDrinkIsCallable())

        match.getDefenseTeam().getDrinkByNumber(2).isDone = true
        assertFalse(match.currentDrinkIsCallable())

        match.getDefenseTeam().getDrinkByNumber(2).isDone = false
        match.getDefenseTeam().getDrinkByNumber(4).isDone = true
        assertTrue(match.currentDrinkIsCallable())

        match.getDefenseTeam().getDrinkByNumber(4).isDone = false
        match.getDefenseTeam().getDrinkByNumber(1).isDone = true
        assertTrue(match.currentDrinkIsCallable())
    }


}