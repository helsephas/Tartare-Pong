package models


import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class PlayerTest {

    private lateinit var player:Player

    @BeforeEach
    fun setUp() {
        this.player = Player(BigDecimal(1),"name",1,1)
    }

    @Test
    fun testInit(){
        assertEquals(this.player.id,BigDecimal(1))
        assertEquals(this.player.name,"name")
        assertEquals(this.player.number,1)
        assertEquals(this.player.teamNumber,1)
        assertFalse(this.player.hasPlayed)
        assertFalse(this.player.trickShotAvailable)
    }
    @Test
    fun testChangeToPlayedState() {
        this.player.changeToPlayedState()
        assertTrue(this.player.hasPlayed)
        assertFalse(this.player.trickShotAvailable)
    }

    @Test
    fun testChangeToPlayedStateForTrickShot() {
        this.player.trickShotAvailable = true
        this.player.changeToPlayedState()
        assertTrue(this.player.hasPlayed)
        assertFalse(this.player.trickShotAvailable)
    }
    @Test
    fun testResetTurn() {
        this.player.resetTurn()
        assertFalse(this.player.hasPlayed)
        assertFalse(this.player.trickShotAvailable)
    }
    @Test
    fun testChangeTrickShot(){
        this.player.changeToTrickShot()
        assertTrue(this.player.trickShotAvailable)
    }

    @Test
    fun testToString(){
        val toString = player.toString()
        assertEquals("Player(id=1, name='name', number=1, teamNumber=1, trickShotAvailable=false)",toString)
    }

    @Test
    fun testEquals(){
        val player2 = Player(BigDecimal(2),"name",2,1)
        assertFalse(player == player2)
        val player3 = Player(BigDecimal(2),"name",2,1)
        assertTrue(player2 == player3)
    }
}