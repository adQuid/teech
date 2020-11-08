package model.shortstate

import model.longstate.LongStateCharacter

class Spawn {

    val x: Int
    val y: Int

    val character: Int

    constructor(x: Int, y: Int, character: Int){
        this.x = x
        this.y = y
        this.character = character
    }

    fun generateCharacter(): ShortStateCharacter{
        return ShortStateCharacter(character, x, y)
    }

}