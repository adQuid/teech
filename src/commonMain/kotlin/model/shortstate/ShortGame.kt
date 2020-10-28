package model.shortstate

import kotlin.math.pow
import kotlin.math.sqrt

class ShortGame {

    val characters = listOf(ShortStateCharacter(1, "armored ekf.png", 200, 500),
            ShortStateCharacter(2, "old bard.png", 500, 400),
            ShortStateCharacter(3, "old bard.png", 400, 300)
    , ShortStateCharacter(4, "old bard.png", 300, 200)
    , ShortStateCharacter(5, "old bard.png", 200, 100))

    val communications = mutableListOf<Communication>()

    fun charactersInRange(coordinate: Coordinate, range: Int): Collection<ShortStateCharacter>{
        return characters.filter {
            sqrt((it.location.x - coordinate.x).toDouble().pow(2) + (it.location.y - coordinate.y).toDouble().pow(2)) <= range
        }
    }

}