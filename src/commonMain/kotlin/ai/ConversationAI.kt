package ai

import controller.ShortStateController
import model.shortstate.Communication
import model.shortstate.Coordinate
import model.shortstate.ShortStateCharacter
import model.shortstate.messagetypes.SweetCaroline
import ui.UIMain
import kotlin.random.Random


class ConversationAI {

    val parent: ShortStateCharacter

    constructor(parent: ShortStateCharacter){
        this.parent = parent
    }

    fun respondToLine(communication: Communication){
        communication.messages.forEach{
            if(it is SweetCaroline){
                parent.say("Bah Bah Bah")
            }
        }
    }

}