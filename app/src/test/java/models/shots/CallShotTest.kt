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

class CallShotTest {

    private lateinit var callShot: CallShot
    private lateinit var player: Player
    private lateinit var drink: Drink

    @BeforeEach
    fun setUp() {
        player = Player(BigDecimal(1),"name",1,1)
        drink = Drink(BigDecimal(1),1,1)
        callShot = CallShot(player,null,1)
    }
    
    @Test
    fun testInit(){
        assertFalse(callShot.isSuccess)
        assertEquals(callShot.shotType,ShotType.CALL)
        assertNull(callShot.drink)
        assertEquals(callShot.shotedBy,player)
        assertEquals(callShot.turnNumber,1)
        assertEquals(callShot.shotImpact,2)
    }
    
    @Test
    fun testScored(){
        callShot = CallShot(player,drink,1)
        assertNotNull(callShot.drink)
        assertTrue(callShot.isSuccess)
    }
}