package model.shortstate.messagetypes

import controller.LongStateController
import model.longstate.Perspective
import model.shortstate.Message
import model.shortstate.MessageFactory

object RequestPerspectiveFactory: MessageFactory() {
    override fun generateMessages(text: String): List<Message> {
        val allTopics = LongStateController.activeLongGame.cultures.flatMap { it.perspectives.map { it.topic } }

        val results = allTopics.filter { text.contains("$it?") }.map { RequestPerspective(
                Perspective(mapOf("topic" to it, "opinion" to 0.0, "minDisposition" to 0.0, "text" to text))
        ) }

        if(results.size > 1){
            return results.subList(0,1)
        } else {
            return results
        }
    }

}

class RequestPerspective(val perspective: Perspective): Message() {

    override fun tooltip(): String {
        return "Ask about ${perspective.topic}"
    }
}