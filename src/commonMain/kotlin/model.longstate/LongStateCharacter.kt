package model.longstate

import controller.LongStateController

class LongStateCharacter {

    val id: Int
    val image: String
    var culture: Culture


    constructor(id: Int, image: String, culture: Culture){
        this.id = id
        this.image = image
        this.culture = culture
    }

}