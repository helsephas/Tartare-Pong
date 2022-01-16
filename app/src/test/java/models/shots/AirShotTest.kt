package models.shots

import junit.framework.TestCase
import models.Player
import org.junit.Test
import java.math.BigDecimal

class AirShotTest : TestCase() {

    private lateinit var airShot: AirShot
    private lateinit var player: Player

    public override fun setUp() {
        super.setUp()
        player = Player(BigDecimal(1),"name",1,1)
        airShot = AirShot(player,null,1)
    }

    @Test
    fun testInit(){
        assertFalse(airShot.isSuccess)
        assertEquals(airShot.turnNumber,1)
        assertNull(airShot.defender)
        assertEquals(airShot.shotedBy,player)
        assertEquals(ShotType.AIR_SHOT,airShot.shotType)
        assertEquals(airShot.shotImpact,1)
    }

    @Test
    fun testHasBeenDefended() {
        assertFalse(airShot.hasBeenDefended())
        airShot = AirShot(player,player,1)
        assertTrue(airShot.hasBeenDefended())
    }
}