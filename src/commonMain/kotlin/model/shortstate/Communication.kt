package model.shortstate

import com.soywiz.korge.view.Container
import com.soywiz.korge.view.Text
import com.soywiz.korge.view.View

class Communication: Entity {

    var age = 0;
    val message: String

    constructor(origin: Coordinate, message: String): super(origin, "korge.png"){
        this.message = message
    }

    override suspend fun display(): View {
        return Text(message)
    }

}