package models

import junit.framework.TestCase
import models.exception.InvalidPlayerNumberException
import org.junit.Assert.*
import org.junit.Test
import java.math.BigDecimal

class TeamTest : TestCase() {

    private lateinit var team: Team

    public override fun setUp() {
        super.setUp()
        this.team = Team(BigDecimal(1), "Team 1", 1)
        team.init("Player 1", "Player 2")
    }

    @Test
    fun testInit() {
        assertEquals(team.id, BigDecimal(1))
        assertEquals(team.name, "Team 1")
        assertEquals(team.number, 1)

        assertEquals(team.players.size, 2)
        assertEquals(team.players[0].name, "Player 1")
        assertEquals(team.players[0].teamNumber, team.number)
        assertEquals(team.players[1].name, "Player 2")
        assertEquals(team.players[1].teamNumber, team.number)

        assertEquals(team.drinks.size, 6)
        assertEquals(team.drinks[0].number, 1)
        assertEquals(team.drinks[0].teamNumber, 1)
        assertEquals(team.drinks[1].number, 2)
        assertEquals(team.drinks[1].teamNumber, 1)
        assertEquals(team.drinks[2].number, 3)
        assertEquals(team.drinks[2].teamNumber, 1)
        assertEquals(team.drinks[3].number, 4)
        assertEquals(team.drinks[3].teamNumber, 1)
        assertEquals(team.drinks[4].number, 5)
        assertEquals(team.drinks[4].teamNumber, 1)
        assertEquals(team.drinks[5].number, 6)
        assertEquals(team.drinks[5].teamNumber, 1)
    }

    @Test
    fun testGetPlayerByNumber() {
        val player1: Player = team.getPlayerByNumber(1)
        assertEquals(player1, team.players[0])
        val player2: Player = team.getPlayerByNumber(2)
        assertEquals(player2, team.players[1])
    }

    @Test
    fun testGetOtherPlayerOnly() {
        val player1: Player = team.getOtherPlayerOnly(2)
        assertEquals(player1, team.players[0])
        val player2: Player = team.getOtherPlayerOnly(1)
        assertEquals(player2, team.players[1])
    }


    @Test(expected = InvalidPlayerNumberException::class)
    fun testGetOtherPlayerOnlyWithUnvalidNumber() {
        try {
            val player3: Player = team.getOtherPlayerOnly(3)
        } catch (e:InvalidPlayerNumberException) {
            assertNotNull(e)
        }
    }

    @Test
    fun testNbDrinksNotDone(){
        assertEquals(team.nbDrinksNotDone(),6)
        team.drinks[0].isDone = true
        assertEquals(team.nbDrinksNotDone(),5)
    }

    @Test
    fun testGetDrinkByNumber() {
        val drink1: Drink = team.getDrinkByNumber(1)
        assertEquals(team.drinks[0], drink1)
        val drink2: Drink = team.getDrinkByNumber(2)
        assertEquals(team.drinks[1], drink2)
        val drink3: Drink = team.getDrinkByNumber(3)
        assertEquals(team.drinks[2], drink3)
        val drink4: Drink = team.getDrinkByNumber(4)
        assertEquals(team.drinks[3], drink4)
        val drink5: Drink = team.getDrinkByNumber(5)
        assertEquals(team.drinks[4], drink5)
        val drink6: Drink = team.getDrinkByNumber(6)
        assertEquals(team.drinks[5], drink6)
    }

    @Test
    fun testResetAllPlayers() {
        team.resetAllPlayers()
        assertFalse(team.players[0].hasPlayed)
        assertFalse(team.players[0].trickShotAvailable)
        assertFalse(team.players[1].hasPlayed)
        assertFalse(team.players[1].trickShotAvailable)
    }

    @Test
    fun testAllDrinksAreDone() {

    }
}