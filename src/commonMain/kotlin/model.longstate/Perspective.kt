package model.longstate

class Perspective {

    val topic: String
    val opinion: Int
    val text: String

    constructor(topic: String, opinion: Int, text: String){
        this.topic = topic
        this.opinion = opinion
        this.text = text
    }


}