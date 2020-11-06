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
        if(text.length > 20){
            return Text(text.substring(0,20) + "...")
        } else {
            return Text(text)
        }
    }

    fun oldEnoughToHear(): Boolean{
        return age == 50
    }

    fun oldEnoughToRespond(): Boolean{
        return age == 5 * text.length
    }

    fun oldEnoughToDie(): Boolean{
        return age > 10 * text.length
    }

    fun splitIntoLines(): List<String>{
        val retval = mutableListOf<String>()

        var nextLine = ""
        text.split(Regex(" +")).forEach {
            if(nextLine.length + it.length > 55) {
                retval.add(nextLine)
                nextLine = ""
            }
            nextLine = nextLine + " " + it
        }

        if(nextLine.length > 0){
            retval.add(nextLine)
        }
        retval.add("")

        return retval.toList()
    }

}