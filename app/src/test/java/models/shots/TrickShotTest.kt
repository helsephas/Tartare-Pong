package models.shots

import junit.framework.TestCase
import models.Drink
import models.Player
import org.junit.Test
import java.math.BigDecimal

class TrickShotTest : TestCase() {

    private lateinit var trickShot: TrickShot
    private lateinit var player: Player
    private lateinit var drink: Drink

    public override fun setUp() {
        super.setUp()
        player = Player(BigDecimal(1),"name",1,1)
        drink = Drink(BigDecimal(1),1,1)
        trickShot = TrickShot(player,null,1)
    }

    @Test
    fun testInit(){
        assertFalse(trickShot.isSuccess)
        assertEquals(trickShot.shotType,ShotType.TRICK_SHOT)
        assertNull(trickShot.drink)
        assertEquals(trickShot.shotedBy,player)
        assertEquals(trickShot.turnNumber,1)
        assertEquals(trickShot.shotImpact,1)
    }

    @Test
    fun testScored(){
        trickShot = TrickShot(player,drink,1)
        assertNotNull(trickShot.drink)
        assertTrue(trickShot.isSuccess)
    }
}