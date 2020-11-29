package model.longstate

import serialize.Serializer
import kotlin.math.roundToInt

class Perspective {

    val topic: String
    val opinion: Int
    val text: String

    constructor(topic: String, opinion: Int, text: String){
        this.topic = topic
        this.opinion = opinion
        this.text = text
    }

    constructor(map: Map<String, Any>){
        topic = map["topic"] as String
        opinion = (map["opinion"] as Double).roundToInt()
        text = map["text"] as String
    }

}