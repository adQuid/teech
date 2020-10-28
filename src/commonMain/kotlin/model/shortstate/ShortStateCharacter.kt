package model.shortstate

import ai.ConversationAI
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.Image
import com.soywiz.korge.view.View
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs
import controller.ShortStateController
import ui.UIMain

class ShortStateCharacter: Entity {

    val id: Int

    var targetX: Int? = null
    var targetY: Int? = null

    val convoAI: ConversationAI

    constructor(id: Int, image: String, x: Int, y: Int): super(Coordinate(x,y), image){
        this.id = id
        convoAI = ConversationAI(this)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ShortStateCharacter

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }

    override suspend fun display(): View {
        return Image(resourcesVfs[image].readBitmap())
    }

    fun say(message: String){
        ShortStateController.activeShortGame.communications
                .add(Communication(Coordinate(location.x, location.y - 10), message))
    }
}