package ai

import model.shortstate.Communication
import model.shortstate.Message
import model.shortstate.ShortStateCharacter
import model.shortstate.messagetypes.GivePerspective
import model.shortstate.messagetypes.Greeting
import model.shortstate.messagetypes.RequestPerspective
import model.shortstate.messagetypes.SweetCaroline
import ui.UIMain

class ConversationReaction(val response: String, val priority: Int, val messages: List<Message> = listOf()) {

}

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
        }
    }

    fun respondToLine(communication: Communication){
        if(communication.speaker == parent || UIMain.player == parent){
            return
        }
        val responseOptions = mutableListOf<ConversationReaction>()

        if(communication.target == parent){
            parent.longCharacter.culture.politeGreetings.forEach {
                if(communication.text.toLowerCase().contains(it.toLowerCase())){
                    parent.improveMoodTowards(25)
                    if(parent.mood > 15){
                        responseOptions.add(ConversationReaction("Thanks, that's very sweet.", 10))
                    }
                }
            }
        }

        communication.messages.forEach{
            if(it is SweetCaroline){
                responseOptions.add( ConversationReaction("Bah Bah Bah", 9999))
            }
            if(it is GivePerspective){
                val myPerspective = parent.surfacePerspectiveOn(it.perspective.topic)
                if(myPerspective != null){
                    if(it.perspective.opinion - parent.surfacePerspectiveOn(it.perspective.topic)!!.opinion > 50){
                        responseOptions.add( ConversationReaction(parent.surfacePerspectiveOn(it.perspective.topic)!!.text, 50, listOf(GivePerspective(myPerspective))))
                    }
                }
            }
            if(communication.target == parent){
                if(it is Greeting){
                    if(thingsIAlreadySaid(communication.speaker).filterIsInstance<Greeting>().isEmpty()){
                        responseOptions.add( ConversationReaction("Hello", 4))
                    } else {
                        responseOptions.add( ConversationReaction("Um... hi", 4))
                    }
                }
                if(it is RequestPerspective){
                    val myPerspective = parent.surfacePerspectiveOn(it.perspective.topic)
                    if(myPerspective != null){
                        responseOptions.add( ConversationReaction(parent.surfacePerspectiveOn(it.perspective.topic)!!.text, 50, listOf(GivePerspective(myPerspective))))
                    } else {
                        responseOptions.add( ConversationReaction("Huh, I've never heard about that...", 10))
                    }
                }
                if(it is GivePerspective){
                    val truePerspective = parent.deepPerspectiveOn(it.perspective.topic)
                    if(truePerspective != null){
                        if(truePerspective!!.opinion > 10 && it.perspective.opinion > 10){
                            parent.improveMoodTowards(30)
                            responseOptions.add(ConversationReaction("It is pretty sweet, isn't it?", 15))
                        } else if(truePerspective!!.opinion < -10 && it.perspective.opinion < -10){
                            parent.improveMoodTowards(30)
                            responseOptions.add(ConversationReaction("I know right, it sucks!", 15))
                        } else {
                            responseOptions.add(ConversationReaction("Good to hear.", 5))
                        }

                    } else {
                        responseOptions.add( ConversationReaction("Huh, I've never heard about that...", 10))
                    }
                }
            }
        }

        if(responseOptions.isNotEmpty()){
            val bestOption = responseOptions.sortedByDescending { it.priority }.first()

            if(bestOption.messages.isEmpty()){
                parent.say(bestOption.response, null)
            } else {
                parent.say(bestOption.response, bestOption.messages, null)
            }
        }
    }

    fun lastThingSaidToMe(character: ShortStateCharacter?): List<String>{
        if(memory.lastOrNull { it.speaker == character } != null){
            return memory.last { it.speaker == character }!!.splitIntoLines()
        } else {
            return listOf()
        }
    }

    private fun thingsIAlreadySaid(character: ShortStateCharacter): Set<Message>{
       return memory.filter { it.speaker == parent && it.target == character }.map {it.messages }.flatten().toSet()
    }

}