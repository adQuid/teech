package model.shortstate

import com.soywiz.korge.view.Text
import com.soywiz.korge.view.View

class Communication: Entity {

    var age = 0
    val speaker: ShortStateCharacter
    val target: ShortStateCharacter?
    val text: String
    val messages: List<Message>

    constructor(origin: Coordinate, speaker: ShortStateCharacter, target: ShortStateCharacter?, message: String, messages: List<Message>): super(origin, "korge.png"){
        this.speaker = speaker
        this.target = target
        this.text = message
        this.messages = messages
    }

    override suspend fun display(): View {
        return Text(text)
    }

}