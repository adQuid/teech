package model.shortstate.messagetypes

import model.shortstate.Message
import model.shortstate.MessageFactory

object GreetingFactory: MessageFactory(){
    val helloWords = listOf("hello", "greetings")


    override fun generateMessages(text: String): List<Message> {
        val words = text.split(" ")

        return helloWords.flatMap { keyword -> words.filter { word -> word == keyword }.map { Greeting() }}
    }

}

class Greeting: Message() {
    override fun tooltip(): String {
        return "Greeting"
    }
}