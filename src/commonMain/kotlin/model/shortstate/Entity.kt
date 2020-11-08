package model.shortstate

import com.soywiz.korge.view.View
import ui.UIMain

abstract class Entity {

    var location: Coordinate
    var needsRedraw = false

    constructor(coordinate: Coordinate){
        this.location = coordinate
    }

    abstract suspend fun display(): View

}