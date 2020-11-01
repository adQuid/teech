package model.shortstate

import ai.ConversationAI
import com.soywiz.korev.MouseButton
import com.soywiz.korge.input.onClick
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.Image
import com.soywiz.korge.view.View
import com.soywiz.korge.view.image
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.file.std.resourcesVfs
import controller.ShortStateController
import ui.UIMain

class ShortStateCharacter: Entity {

    val id: Int

    var targetX: Int? = null
    var targetY: Int? = null

    private var targetCharacter: ShortStateCharacter? = null

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
        println("displaying")
        val retval = Image(resourcesVfs[image].readBitmap())
        if(UIMain.player!!.targetCharacter == this){
            retval.image(resourcesVfs["target.png"].readBitmap())
        }
        retval.onClick { mouseEvents -> if(mouseEvents.lastEvent.button == MouseButton.LEFT){ UIMain.player!!.target(this) }}
        return retval
    }

    fun say(message: String, target: ShortStateCharacter? = null){
        ShortStateController.activeShortGame.communications
                .add(Communication(Coordinate(location.x, location.y - 10), this, target, message, Message.messageListFromString(message)))
    }

    fun getTarget(): ShortStateCharacter?{
        return targetCharacter
    }

    fun target(target: ShortStateCharacter?){
        if(UIMain.player == this){
            UIMain.needToUpdateTargetView = true
            if(targetCharacter != null){
                targetCharacter!!.needsRedraw = true
            }
            if(target != null){
                target.needsRedraw = true
            }
        }
        this.targetCharacter = target

    }

    fun setTargetPosition(coordinate: Coordinate){
        this.targetX = coordinate.x
        this.targetY = coordinate.y
    }

    fun move(x: Double, y: Double){
        location = location.plus(Coordinate(x.toInt(), y.toInt()))
        if(UIMain.charactersBeingDrawn.containsKey(this)){
            UIMain.charactersBeingDrawn[this]!!.x = location.x.toDouble()
            UIMain.charactersBeingDrawn[this]!!.y = location.y.toDouble()
        }
    }
}