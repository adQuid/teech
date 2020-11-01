package ui

import InputHandler
import com.soywiz.kds.Stack
import com.soywiz.klock.milliseconds
import com.soywiz.korev.Key
import com.soywiz.korge.Korge
import com.soywiz.korge.input.onClick
import com.soywiz.korge.input.onKeyDown
import com.soywiz.korge.time.delay
import com.soywiz.korge.tween.get
import com.soywiz.korge.tween.tween
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.async.async
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.Rectangle
import com.soywiz.korma.interpolation.Easing
import controller.ShortStateController
import model.shortstate.*
import kotlin.math.max
import kotlin.math.min

object UIMain {

    var width = 924
    var height = 924

    var frameDelay = 20

    val charactersBeingDrawn = mutableMapOf<ShortStateCharacter, View>()
    val communicationsBeingDrawn = mutableMapOf<Communication, View>()

    var needToUpdateTargetView = false
    var targetViews = setOf<View>()

    val menuOverlays = Stack<UILayer>()

    val baseInputHandler = InputHandler()

    var player: ShortStateCharacter? = null

    fun defocus(){
        if(menuOverlays.isNotEmpty()){
            menuOverlays.pop()
        }
    }

    suspend fun makeUI() = Korge(width = width, height = height, bgcolor = Colors["#2b2b2b"]) {
        onClick{
            if(menuOverlays.isNotEmpty()){
                menuOverlays.peek()!!.inputHandler.handleInput(it.lastEvent)
            } else {
                baseInputHandler.handleInput(it.lastEvent)
            }
        }

        onKeyDown {
            if(it.key == Key.ESCAPE){
                defocus()
            } else {
                if(menuOverlays.isNotEmpty()){
                    menuOverlays.peek()!!.inputHandler.handleInput(it.key)
                } else {
                    baseInputHandler.handleInput(it.key)
                }
            }
        }

        player = ShortStateController.activeShortGame.characters[0]

        fun allViewsThatShouldBeHere(): Collection<View>{
            var retval = charactersBeingDrawn.values + communicationsBeingDrawn.values + targetViews
            if(menuOverlays.peek() != null){
                retval += menuOverlays.peek()!!.views()
            }

            return retval
        }

        suspend fun <T: Entity> addEntities(entityList: Collection<T>, entityMap: MutableMap<T, View>, startIndex: Int){
            var i = startIndex

            entityList.filter{it.needsRedraw}.forEach {
                println("found something that needs redraw")
                it.needsRedraw = false
                entityMap.remove(it)
            }

            entityList.filter{!entityMap.keys.contains(it as Entity) || it.needsRedraw}.forEach{
                entityMap[it] = it.display()
                entityMap[it]!!.position(it.location.x, it.location.y)
                entityMap[it]!!.scale = 0.8

                addChildAt(entityMap[it]!!, i++)
            }

            entityMap.filter{!entityList.contains(it.key)}.forEach {
                entityMap.remove(it.key)
            }
        }

        suspend fun updateTargetView(){
            needToUpdateTargetView = false
            if(player != null && player!!.getTarget() != null){
                val targetProfile = Image(resourcesVfs[player!!.getTarget()!!.image].readBitmap())
                targetProfile.x = width * 0.8
                targetProfile.y = height - (width * 0.2)
                targetProfile.width = width * 0.2
                targetProfile.height = width * 0.2

                val textWindow = Image(resourcesVfs["textbox.png"].readBitmap())
                textWindow.x = 0.0
                textWindow.y = height * 0.8
                textWindow.width = width * 0.8
                textWindow.height = height * 0.2
                val text = textWindow.text(player!!.convoAI.lastThingSaidToMe(player!!.getTarget()))
                text.y = height * 0.01
                text.x = width * 0.01

                targetViews = setOf(
                    targetProfile,
                    textWindow
                )
            } else {
                targetViews = setOf()
            }
        }

        while(true){
            delay(frameDelay.milliseconds)

            if(player != null && needToUpdateTargetView){
                    updateTargetView()
            }

            addEntities(ShortStateController.activeShortGame.characters, charactersBeingDrawn, 0)
            addEntities(ShortStateController.activeShortGame.communications, communicationsBeingDrawn, charactersBeingDrawn.size)

            var i = 0
            targetViews.forEach{
                addChildAt(it, charactersBeingDrawn.size + communicationsBeingDrawn.size + i++)
            }
            if(menuOverlays.peek() != null){
                menuOverlays.peek()!!.views().filter { !children.contains(it) }.forEach {
                    addChildAt(it, charactersBeingDrawn.size + communicationsBeingDrawn.size + i++)
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