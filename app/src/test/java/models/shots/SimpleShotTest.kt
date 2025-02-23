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

class SimpleShotTest {

    private lateinit var simpleShot: SimpleShot
    private lateinit var player: Player
    private lateinit var drink: Drink

    @BeforeEach
    fun setUp() {
        player = Player(BigDecimal(1),"name",1,1)
        drink = Drink(BigDecimal(1),1,1)
        simpleShot = SimpleShot(player,null,1)
    }

    @Test
    fun testInit(){
        assertFalse(simpleShot.isSuccess)
        assertEquals(simpleShot.shotType,ShotType.SIMPLE)
        assertNull(simpleShot.drink)
        assertEquals(simpleShot.shotedBy,player)
        assertEquals(simpleShot.turnNumber,1)
        assertEquals(simpleShot.shotImpact,1)
    }

    @Test
    fun testScored(){
        simpleShot = SimpleShot(player,drink,1)
        assertNotNull(simpleShot.drink)
        assertTrue(simpleShot.isSuccess)
    }
}