package model.shortstate.messagetypes

import controller.LongStateController
import model.longstate.Perspective
import model.shortstate.Message
import model.shortstate.MessageFactory

object RequestPerspectiveFactory: MessageFactory() {
    override fun generateMessages(text: String): List<Message> {
        val allTopics = LongStateController.activeLongGame.cultures.flatMap { it.perspectives.map { it.topic } }

        return allTopics.filter { text.contains("$it?") }.map { RequestPerspective(Perspective(it, 0, text)) }
    }

}

class RequestPerspective(val perspective: Perspective): Message() {

    override fun tooltip(): String {
        return "Ask about ${perspective.topic}"
    }
}