package model.shortstate.messagetypes

import model.shortstate.Message
import model.shortstate.MessageFactory

object SweetCarolineFactory: MessageFactory() {
    override fun generateMessages(text: String): List<Message> {
        return Regex("sweet caroline").findAll(text).map { SweetCaroline() }.toList()
    }
}

class SweetCaroline: Message() {

    override fun tooltip(): String {
        return "Sweet Caroline"
    }

}