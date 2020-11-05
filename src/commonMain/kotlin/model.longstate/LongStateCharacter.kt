package model.longstate

class LongStateCharacter {

    val id: Int
    var culture: Culture

    constructor(id: Int, culture: Culture){
        this.id = id
        this.culture = culture
    }

}