package model.shortstate.messagetypes

import controller.LongStateController
import model.longstate.LongStateCharacter
import model.longstate.Perspective
import model.shortstate.Message
import model.shortstate.MessageFactory

object GivePerspectiveFactory: MessageFactory() {
    override fun generateMessages(text: String): List<Message> {
        val allTopics = LongStateController.activeLongGame.cultures.flatMap { it.perspectives.map { it.topic } }

        return allTopics.filter { text.contains(it) }.map { GivePerspective(Perspective(it, 0, text)) }
    }

}

class GivePerspective(val perspective: Perspective): Message() {

    override fun tooltip(): String {
        if(perspective.opinion > 10){
            return "Approve of ${perspective.topic}"
        } else if(perspective.opinion < -10){
            return "Disapprove of ${perspective.topic}"
        } else {
            return "Mention ${perspective.topic}"
        }
    }
}