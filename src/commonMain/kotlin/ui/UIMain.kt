package ui

import com.soywiz.klock.milliseconds
import com.soywiz.korge.Korge
import com.soywiz.korge.input.onClick
import com.soywiz.korge.input.onKeyDown
import com.soywiz.korge.input.onKeyUp
import com.soywiz.korge.time.delay
import com.soywiz.korge.tween.get
import com.soywiz.korge.tween.tween
import com.soywiz.korge.ui.TextButton
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.async.launchImmediately
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.degrees
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

    var player: ShortStateCharacter? = null

    suspend fun makeUI() = Korge(width = width, height = height, bgcolor = Colors["#2b2b2b"]) {
        var clicks = 1
        onClick{
            println(clicks++)
            InputHandler.handleInput(it.lastEvent)
        }

        onKeyDown {
            InputHandler.handleInput(it.key)
        }

        val label = TextButton(256.0, 32.0, "Enabled Button")
        label.y = 400.0
        label.onClick { println("button pressed") }
        addChild(label)

        player = ShortStateController.activeShortGame.characters[0]

        suspend fun <T: Entity> addEntities(entityList: Collection<T>, entityMap: MutableMap<T, View>, startIndex: Int){
            var i = startIndex

            entityList.filter{!entityMap.keys.contains(it as Entity)}.forEach{
                entityMap[it] = it.display()
                entityMap[it]!!.position(it.location.x, it.location.y)
                entityMap[it]!!.scale = 0.8

                addChildAt(entityMap[it]!!, i++)
            }

            entityMap.keys.filter { !entityList.contains(it) }.forEach {
                entityMap[it]!!.removeFromParent()
            }
        }

        while(true){
            delay(frameDelay.milliseconds)

            addEntities(ShortStateController.activeShortGame.characters, charactersBeingDrawn, 0)
            addEntities(ShortStateController.activeShortGame.communications, communicationsBeingDrawn, charactersBeingDrawn.size)

            charactersBeingDrawn.filter{it.key.location.x != null && it.value.x != it.key.location.x.toDouble()}.forEach {
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

                if(newX != it.value.x){
                    launchImmediately {
                        image.tween(image::x[image.x, newX], time = frameDelay.milliseconds, easing = Easing.LINEAR)
                    }
                }

            }
        }
    }


}