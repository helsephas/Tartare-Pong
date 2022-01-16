package models

import junit.framework.TestCase
import org.junit.Assert.*
import org.junit.Test
import java.math.BigDecimal

class DrinkTest : TestCase() {

    private lateinit var drink: Drink

    public override fun setUp() {
        super.setUp()
        this.drink = Drink(BigDecimal(1),1,1)
    }

    @Test
    fun testInit(){
        assertEquals(drink.id,BigDecimal(1))
        assertEquals(drink.number,1)
        assertEquals(drink.teamNumber,1)
        assertFalse(drink.isDone)
    }
}