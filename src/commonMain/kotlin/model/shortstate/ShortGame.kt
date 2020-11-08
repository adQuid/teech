package model.shortstate

import kotlin.math.pow
import kotlin.math.sqrt

class ShortGame {

    val characters: List<ShortStateCharacter>
    val communications = mutableListOf<Communication>()

    constructor(encounter: Encounter){
        characters = encounter.spawns.map { it.generateCharacter() }
    }

    fun charactersInRange(coordinate: Coordinate, range: Int): Collection<ShortStateCharacter>{
        return characters.filter {
            sqrt((it.location.x - coordinate.x).toDouble().pow(2) + (it.location.y - coordinate.y).toDouble().pow(2)) <= range
        }
    }

}