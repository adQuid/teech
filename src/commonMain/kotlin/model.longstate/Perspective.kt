package model.longstate

import serialize.Serializer
import kotlin.math.roundToInt

class Perspective {

    val topic: String
    val opinion: Int
    val minDisposition: Int
    val text: String

    constructor(map: Map<String, Any>){
        topic = map["topic"] as String
        opinion = (map["opinion"] as Double).roundToInt()
        minDisposition = (map["minDisposition"] as Double).roundToInt()
        text = map["text"] as String
    }

}