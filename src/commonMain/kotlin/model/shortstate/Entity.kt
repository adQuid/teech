package model.shortstate

import com.soywiz.korge.view.View

abstract class Entity {

    var location: Coordinate
    val image: String
    var needsRedraw = false

    constructor(coordinate: Coordinate, image: String){
        this.location = coordinate
        this.image = image
    }

    abstract suspend fun display(): View

}