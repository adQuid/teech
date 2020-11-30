package model.shortstate.messagetypes

import controller.LongStateController
import model.longstate.Perspective
import model.shortstate.Message
import model.shortstate.MessageFactory

object GivePerspectiveFactory: MessageFactory() {
    val goodWords = listOf("lovely", "good", "nice")
    val badWords = listOf("shitty", "bad", "evil")

    override fun generateMessages(text: String): List<Message> {
        val allTopics = LongStateController.activeLongGame.cultures.flatMap { it.perspectives.map { it.topic } }

        val results = allTopics.filter { text.contains(Regex("$it[ .,;:]")) }.map { GivePerspective(
                Perspective(mapOf(
                        "topic" to it,
                        "opinion" to
                                10.0 * goodWords.filter { text.toLowerCase().contains(it.toLowerCase()) }.size
                        - 10.0 * badWords.filter { text.toLowerCase().contains(it.toLowerCase()) }.size,
                        "minDisposition" to 0.0, "text" to text
                ))
        ) }

        if(results.size > 1){
            return results.subList(0,1)
        } else {
            return results
        }
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