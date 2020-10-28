package ui

import com.soywiz.klock.milliseconds
import com.soywiz.korge.Korge
import com.soywiz.korge.input.onClick
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
import model.shortstate.*
import kotlin.math.max
import kotlin.math.min

object UIMain {

    var width = 1024
    var height = 1024

    var frameDelay = 20

    val charactersBeingDrawn = mutableMapOf<ShortStateCharacter, View>()
    val communicationsBeingDrawn = mutableMapOf<Communication, View>()

    suspend fun makeUI() = Korge(width = width, height = height, bgcolor = Colors["#2b2b2b"]) {
        onClick{
            InputHandler.handleInput(it.lastEvent)
        }

        onKeyUp {
            InputHandler.handleInput(it.key)
        }

        val label = TextButton(256.0, 32.0, "Enabled Button")
        label.y = 400.0
        label.onClick { println("button pressed") }
        addChild(label)

        suspend fun <T: Entity> addEntities(entityList: Collection<T>, entityMap: MutableMap<T, View>, startIndex: Int){
            var i = startIndex

            entityList.filter{!entityMap.keys.contains(it as Entity)}.forEach{
                entityMap[it] = it.display()
                entityMap[it]!!.position(it.location.x, it.location.y)

                addChildAt(entityMap[it]!!, i++)
            }
        }

        while(true){
            delay(frameDelay.milliseconds)

            addEntities(ShortGame.characters, charactersBeingDrawn, 0)
            addEntities(ShortGame.communications, communicationsBeingDrawn, charactersBeingDrawn.size)

            charactersBeingDrawn.filter{it.key.targetX != null && it.value.x != it.key.targetX!!.toDouble()}.forEach {
                val image = it.value

                val pixelsToMove = 100 * frameDelay / 1000

                var newX =
                        if(it.value.x > it.key.targetX!!.toDouble()){
                            max(it.value.x - pixelsToMove, it.key.targetX!!.toDouble())
                        } else if(it.value.x < it.key.targetX!!.toDouble()){
                            min(it.value.x + pixelsToMove, it.key.targetX!!.toDouble())
                        } else {
                            it.value.x
                        }

                if(newX != it.value.x){
                    it.key.location = Coordinate(newX.toInt(), it.key.location.y)
                    launchImmediately {
                        image.tween(image::x[image.x, newX], time = frameDelay.milliseconds, easing = Easing.LINEAR)
                    }
                }

            }
        }
    }


}