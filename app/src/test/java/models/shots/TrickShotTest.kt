package models.shots

import models.Drink
import models.Player
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class TrickShotTest {

    private lateinit var trickShot: TrickShot
    private lateinit var player: Player
    private lateinit var drink: Drink

    @BeforeEach
    fun setUp() {
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