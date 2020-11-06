package controller

import kotlinx.coroutines.delay
import model.shortstate.Coordinate
import model.shortstate.ShortGame
import ui.UIMain
import kotlin.math.abs
import kotlin.math.sqrt

object ShortStateController {

    var activeShortGame: ShortGame? = null

    suspend fun run(){
        println("got here")
        while(activeShortGame != null){
            delay(UIMain.frameDelay.toLong())

            val pixelsToMove = 100 * UIMain.frameDelay / 1000
            activeShortGame!!.characters.forEach {
                if(it.targetX != null && it.targetY!= null){
                    val xToMove = it.targetX!! - it.location.x
                    val yToMove = it.targetY!! - it.location.y

                    val totalDistance = sqrt(((xToMove * xToMove) + (yToMove * yToMove)).toDouble())

                    if(abs(xToMove) + abs(yToMove) > 5){
                        it.move(xToMove/totalDistance*pixelsToMove, yToMove/totalDistance*pixelsToMove)
                    }

                }
            }

            activeShortGame!!.communications.filter{it.oldEnoughToHear()}.forEach{communication ->
                activeShortGame!!.charactersInRange(communication.location, 400).forEach { recipient ->
                    recipient.convoAI.hear(communication)
                }
            }

            activeShortGame!!.communications.filter{it.oldEnoughToRespond()}.forEach{communication ->
                activeShortGame!!.charactersInRange(communication.location, 400).forEach { recipient ->
                    recipient.convoAI.respondToLine(communication)
                }
            }
            activeShortGame!!.communications.forEach { it.age++ }
            activeShortGame!!.communications.removeAll { it.oldEnoughToDie() }
        }
    }

}