package models

import models.shots.ShotType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class MatchTest {
    
    private lateinit var match:Match

    @BeforeEach
    fun setUp() {
        this.match = Match().startGame()
    }
    
    @Test
    fun testStartGame(){
        assertNotNull(match)
        assertEquals(match.teams.size,2)
        testTeamA(match.teams[0])
        testTeamB(match.teams[1])
        assertEquals(match.teams[0].players[0],match.currentPlayerPlaying)
        assertNull(match.currentDrinkSelected)
        assertEquals(ShotType.SIMPLE,match.currentShotType)
        assertFalse(match.hasFailedDefense)
        assertNull(match.shotImpact)
        assertEquals(1,match.turnNumber)
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


    private fun testTeamA(team: Team){
        assertEquals(BigDecimal(1),team.id)
        assertEquals("Team A",team.name)
        assertEquals(1,team.number)
        assertEquals(2,team.players.size)
        testPlayerA(team)
        testDrinksA(team)
    }
    private fun testPlayerA(team: Team){
        val player1 = team.players[0]
        assertEquals(BigDecimal(1),player1.id)
        assertEquals("Player 1",player1.name)
        assertEquals(1,player1.number)
        assertEquals(1,player1.teamNumber)

        val player2 = team.players[1]
        assertEquals(BigDecimal(2),player2.id)
        assertEquals("Player 2",player2.name)
        assertEquals(2,player2.number)
        assertEquals(1,player2.teamNumber)
    }

    private fun testDrinksA(team: Team){
        val drink1 = team.drinks[0]
        assertEquals(BigDecimal(1),drink1.id)
        assertFalse(drink1.isDone)
        assertEquals(1,drink1.number)
        assertEquals(1,drink1.teamNumber)

        val drink2 = team.drinks[1]
        assertEquals(BigDecimal(2),drink2.id)
        assertFalse(drink2.isDone)
        assertEquals(2,drink2.number)
        assertEquals(1,drink2.teamNumber)

        val drink3 = team.drinks[2]
        assertEquals(BigDecimal(3),drink3.id)
        assertFalse(drink3.isDone)
        assertEquals(3,drink3.number)
        assertEquals(1,drink3.teamNumber)

        val drink4 = team.drinks[3]
        assertEquals(BigDecimal(4),drink4.id)
        assertFalse(drink4.isDone)
        assertEquals(4,drink4.number)
        assertEquals(1,drink4.teamNumber)

        val drink5 = team.drinks[4]
        assertEquals(BigDecimal(5),drink5.id)
        assertFalse(drink5.isDone)
        assertEquals(5,drink5.number)
        assertEquals(1,drink5.teamNumber)

        val drink6 = team.drinks[5]
        assertEquals(BigDecimal(6),drink6.id)
        assertFalse(drink6.isDone)
        assertEquals(6,drink6.number)
        assertEquals(1,drink6.teamNumber)
    }

    private fun testTeamB(team: Team){
        assertEquals(BigDecimal(2),team.id)
        assertEquals("Team B",team.name)
        assertEquals(2,team.number)
        assertEquals(2,team.players.size)
        testPlayerB(team)
        testDrinksB(team)
    }

    private fun testPlayerB(team: Team){
        val player3 = team.players[0]
        assertEquals(BigDecimal(1),player3.id)
        assertEquals("Player 3",player3.name)
        assertEquals(1,player3.number)
        assertEquals(2,player3.teamNumber)

        val player2 = team.players[1]
        assertEquals(BigDecimal(2),player2.id)
        assertEquals("Player 4",player2.name)
        assertEquals(2,player2.number)
        assertEquals(2,player2.teamNumber)
    }

    private fun testDrinksB(team: Team){
        val drink1 = team.drinks[0]
        assertEquals(BigDecimal(1),drink1.id)
        assertFalse(drink1.isDone)
        assertEquals(1,drink1.number)
        assertEquals(2,drink1.teamNumber)

        val drink2 = team.drinks[1]
        assertEquals(BigDecimal(2),drink2.id)
        assertFalse(drink2.isDone)
        assertEquals(2,drink2.number)
        assertEquals(2,drink2.teamNumber)

        val drink3 = team.drinks[2]
        assertEquals(BigDecimal(3),drink3.id)
        assertFalse(drink3.isDone)
        assertEquals(3,drink3.number)
        assertEquals(2,drink3.teamNumber)

        val drink4 = team.drinks[3]
        assertEquals(BigDecimal(4),drink4.id)
        assertFalse(drink4.isDone)
        assertEquals(4,drink4.number)
        assertEquals(2,drink4.teamNumber)

        val drink5 = team.drinks[4]
        assertEquals(BigDecimal(5),drink5.id)
        assertFalse(drink5.isDone)
        assertEquals(5,drink5.number)
        assertEquals(2,drink5.teamNumber)

        val drink6 = team.drinks[5]
        assertEquals(BigDecimal(6),drink6.id)
        assertFalse(drink6.isDone)
        assertEquals(6,drink6.number)
        assertEquals(2,drink6.teamNumber)
    }
}