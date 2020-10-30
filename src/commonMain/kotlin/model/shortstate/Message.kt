package model.shortstate

import model.shortstate.messagetypes.SweetCaroline
import model.shortstate.messagetypes.SweetCarolineFactory

abstract class Message {

    companion object{
        val factoriesInOrder = listOf(
                SweetCarolineFactory()
        )

        fun messageListFromString(s: String): List<Message>{
            return factoriesInOrder.map { it.generateMessages(s.toLowerCase()) }.flatten()
        }
    }

    abstract fun tooltip(): String

}

abstract class MessageFactory {
    abstract fun generateMessages(text: String): List<Message>
}