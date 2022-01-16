package models.exception

import java.lang.Exception

class InvalidPlayerNumberException(number:Int) : Exception("Player number :$number not in the team") {

}