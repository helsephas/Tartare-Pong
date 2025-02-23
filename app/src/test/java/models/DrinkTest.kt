package models

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class DrinkTest {

    private lateinit var drink: Drink

    @BeforeEach
    fun setUp() {
        this.drink = Drink(BigDecimal(1),1,1)
    }

    @Test
    fun testInit(){
        assertEquals(drink.id,BigDecimal(1))
        assertEquals(drink.number,1)
        assertEquals(drink.teamNumber,1)
        assertFalse(drink.isDone)
    }

    @Test
    fun testToString(){
        val toString = drink.toString()
        assertEquals("Drink(id=1, number=1, isDone=false)\n",toString)
    }

    @Test
    fun testEquals(){
        val drink2 = Drink(BigDecimal(2),2,1)
        assertFalse(drink == drink2)
        val drink3 = Drink(BigDecimal(2),2,1)
        assertTrue(drink2 == drink3)
    }
}