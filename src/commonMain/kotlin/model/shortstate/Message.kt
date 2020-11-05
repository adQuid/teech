package model.shortstate

import model.shortstate.messagetypes.*

abstract class Message {

    companion object{
        val factoriesInOrder = listOf(
                SweetCarolineFactory,
                GreetingFactory,
                RequestPerspectiveFactory,
                GivePerspectiveFactory
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