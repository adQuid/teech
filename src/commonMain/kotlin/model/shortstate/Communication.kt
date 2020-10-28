package model.shortstate

import com.soywiz.korge.view.Container
import com.soywiz.korge.view.Text
import com.soywiz.korge.view.View

class Communication: Entity {

    var age = 0
    val speaker: ShortStateCharacter
    val message: String

    constructor(origin: Coordinate, speaker: ShortStateCharacter, message: String): super(origin, "korge.png"){
        this.speaker = speaker
        this.message = message
    }

    override suspend fun display(): View {
        return Text(message)
    }

}