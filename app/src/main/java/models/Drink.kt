package models

import java.math.BigDecimal

class Drink(var id: BigDecimal = BigDecimal(0),
            var number: Int = 0,
            var teamNumber: Int = 0) {
    var isDone: Boolean = false

    override fun toString(): String {
        return "Drink(id=$id, number=$number, isDone=$isDone)\n"
    }


}