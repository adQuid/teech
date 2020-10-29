package ui

import com.soywiz.kds.Stack
import com.soywiz.klock.milliseconds
import com.soywiz.korge.Korge
import com.soywiz.korge.input.onClick
import com.soywiz.korge.input.onKeyDown
import com.soywiz.korge.time.delay
import com.soywiz.korge.tween.get
import com.soywiz.korge.tween.tween
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korio.async.async
import com.soywiz.korma.interpolation.Easing
import controller.ShortStateController
import model.shortstate.*
import kotlin.math.max
import kotlin.math.min

object UIMain {

    var width = 1024
    var height = 1024

    var frameDelay = 20

    val charactersBeingDrawn = mutableMapOf<ShortStateCharacter, View>()
    val communicationsBeingDrawn = mutableMapOf<Communication, View>()
    val menuOverlays = Stack<UILayer>()

    var player: ShortStateCharacter? = null

    fun defocus(){
        if(menuOverlays.isNotEmpty()){
            menuOverlays.pop()
        }
    }

    suspend fun makeUI() = Korge(width = width, height = height, bgcolor = Colors["#2b2b2b"]) {
        onClick{
            InputHandler.handleInput(it.lastEvent)
        }

        onKeyDown {
            InputHandler.handleInput(it.key)
        }

        player = ShortStateController.activeShortGame.characters[0]

        fun allViewsThatShouldBeHere(): Collection<View>{
            if(menuOverlays.peek() != null){
                return charactersBeingDrawn.values + communicationsBeingDrawn.values + menuOverlays.peek()!!.views()
            } else {
                return charactersBeingDrawn.values + communicationsBeingDrawn.values
            }
        }

        suspend fun <T: Entity> addEntities(entityList: Collection<T>, entityMap: MutableMap<T, View>, startIndex: Int){
            var i = startIndex

            entityList.filter{!entityMap.keys.contains(it as Entity)}.forEach{
                entityMap[it] = it.display()
                entityMap[it]!!.position(it.location.x, it.location.y)
                entityMap[it]!!.scale = 0.8

                addChildAt(entityMap[it]!!, i++)
            }
                    }

        while(true){
            delay(frameDelay.milliseconds)

            addEntities(ShortStateController.activeShortGame.characters, charactersBeingDrawn, 0)
            addEntities(ShortStateController.activeShortGame.communications, communicationsBeingDrawn, charactersBeingDrawn.size)
            if(menuOverlays.peek() != null){
                var i = 0
                menuOverlays.peek()!!.views().filter { !children.contains(it) }.forEach {
                    addChildAt(it, charactersBeingDrawn.size + communicationsBeingDrawn.size + i)
                }
            }

            children.filter{!allViewsThatShouldBeHere().contains(it)}.forEach{
                it.removeFromParent()
            }

            charactersBeingDrawn.filter{(it.key.location.x != null && it.value.x != it.key.location.x.toDouble()) || (it.key.location.y != null && it.value.y != it.key.location.y.toDouble())}
                    .forEach {
                val image = it.value

                val pixelsToMove = 100 * frameDelay / 1000

                var newX =
                        if(it.value.x > it.key.location.x!!.toDouble()){
                            max(it.value.x - pixelsToMove, it.key.location.x!!.toDouble())
                        } else if(it.value.x < it.key.location.x!!.toDouble()){
                            min(it.value.x + pixelsToMove, it.key.location.x!!.toDouble())
                        } else {
                            it.value.x
                        }

                var newY =
                        if(it.value.y > it.key.location.y!!.toDouble()){
                            max(it.value.y - pixelsToMove, it.key.location.y!!.toDouble())
                        } else if(it.value.y < it.key.location.y!!.toDouble()){
                            min(it.value.y + pixelsToMove, it.key.location.y!!.toDouble())
                        } else {
                            it.value.y
                        }

                if(newX != it.value.x || newY != it.value.y){
                    async {
                        image.tween(image::x[image.x, newX], time = frameDelay.milliseconds, easing = Easing.LINEAR)
                    }
                    async {
                        image.tween(image::y[image.y, newY], time = frameDelay.milliseconds, easing = Easing.LINEAR)
                    }
                }

            }
        }
    }


}