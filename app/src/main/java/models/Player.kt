package models

import java.math.BigDecimal

class Player( var id: BigDecimal = BigDecimal(0),
              var name: String = "", var number:Int, var teamNumber:Int) {
}