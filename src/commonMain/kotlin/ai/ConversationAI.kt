package ai

import controller.ShortStateController
import model.shortstate.Communication
import model.shortstate.Coordinate
import model.shortstate.Message
import model.shortstate.ShortStateCharacter
import model.shortstate.messagetypes.Greeting
import model.shortstate.messagetypes.RequestPerspective
import model.shortstate.messagetypes.SweetCaroline
import ui.UIMain
import kotlin.random.Random


class ConversationAI {

    val parent: ShortStateCharacter

    val memory: MutableList<Communication>

    constructor(parent: ShortStateCharacter){
        this.parent = parent
        this.memory = mutableListOf()
    }

    fun hear(communication: Communication){
        memory.add(communication)

        if(UIMain.player == parent){
            UIMain.needToUpdateTargetView = true
        } else if(communication.speaker != parent && UIMain.player != parent){
            respondToLine(communication)
        }
    }

    fun respondToLine(communication: Communication){
        communication.messages.forEach{
            if(it is SweetCaroline){
                parent.say("Bah Bah Bah")
            }
            if(communication.target == parent){
                if(it is Greeting){
                    if(thingsIAlreadySaid(communication.speaker).filterIsInstance<Greeting>().isEmpty()){
                        parent.say("Hello", communication.speaker)
                    } else {
                        parent.say("Umm... hi", communication.speaker)
                    }
                }
                if(it is RequestPerspective){
                    if(parent.longCharacter.culture.perspectiveOn(it.perspective.topic) != null){
                        parent.say(parent.longCharacter.culture.perspectiveOn(it.perspective.topic)!!.text)
                    } else {
                        parent.say("Huh, I've never heard about that.")
                    }
                }
            }
        }
    }

    fun lastThingSaidToMe(character: ShortStateCharacter?): String{
        if(memory.lastOrNull { it.speaker == character } != null){
            return memory.last { it.speaker == character }!!.text
        } else {
            return ""
        }
    }

    private fun thingsIAlreadySaid(character: ShortStateCharacter): Set<Message>{
       return memory.filter { it.speaker == parent && it.target == character }.map {it.messages }.flatten().toSet()
    }

}