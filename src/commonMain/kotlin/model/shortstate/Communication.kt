package model.shortstate

import com.soywiz.korge.view.Container
import com.soywiz.korge.view.Text
import com.soywiz.korge.view.View

class Communication: Entity {

    val message = "Sweet Caroline"

    constructor(origin: Coordinate): super(origin, "korge.png"){

    }

    override suspend fun display(): View {
        return Text(message)
    }

}