package models.shots

import junit.framework.TestCase
import models.Drink
import models.DrinkTest
import models.Player
import org.junit.Test
import java.math.BigDecimal

class BounceShotTest : TestCase() {

    private lateinit var bounceShot: BounceShot
    private lateinit var player: Player
    private lateinit var drink: Drink

    public override fun setUp() {
        super.setUp()
        player = Player(BigDecimal(1),"name",1,1)
        drink = Drink(BigDecimal(1),1,1)
        bounceShot = BounceShot(player,null,null,1)
    }

    @Test
    fun testInit(){
        assertFalse(bounceShot.isSuccess)
        assertEquals(bounceShot.shotType,ShotType.BOUNCE)
        assertNull(bounceShot.defender)
        assertNull(bounceShot.drink)
        assertEquals(bounceShot.shotedBy,player)
        assertEquals(bounceShot.turnNumber,1)
        assertEquals(bounceShot.shotImpact,2)
    }

    @Test
    fun testScored(){
        bounceShot = BounceShot(player,drink,null,1)
        assertNull(bounceShot.defender)
        assertNotNull(bounceShot.drink)
        assertTrue(bounceShot.isSuccess)
    }

    @Test
    fun testDefended(){
        bounceShot = BounceShot(player,drink,player,1,true)
        assertNotNull(bounceShot.defender)
        assertFalse(bounceShot.isSuccess)
    }

    @Test
    fun testDefendedFailed(){
        bounceShot = BounceShot(player,drink,player,1)
        assertNotNull(bounceShot.defender)
        assertFalse(bounceShot.isSuccess)
    }
}