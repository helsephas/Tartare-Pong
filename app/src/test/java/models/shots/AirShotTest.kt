package models.shots

import models.Player
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class AirShotTest {

    private lateinit var airShot: AirShot
    private lateinit var player: Player

    @BeforeEach
    fun setUp() {
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