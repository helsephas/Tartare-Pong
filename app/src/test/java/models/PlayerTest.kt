package models

import junit.framework.TestCase
import org.junit.Assert.*
import org.junit.Test
import java.math.BigDecimal

class PlayerTest : TestCase() {

    private lateinit var player:Player

    public override fun setUp() {
        super.setUp()
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
}