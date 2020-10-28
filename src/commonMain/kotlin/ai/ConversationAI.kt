package ai

import controller.ShortStateController
import model.shortstate.Communication
import model.shortstate.Coordinate
import model.shortstate.ShortStateCharacter
import ui.UIMain
import kotlin.random.Random


class ConversationAI {

    val parent: ShortStateCharacter

    constructor(parent: ShortStateCharacter){
        this.parent = parent
    }

    fun respondToLine(communication: Communication){
        if(communication.message.contains("Sweet")){
            parent.say("Bah Bah Bah")
        }
    }

}