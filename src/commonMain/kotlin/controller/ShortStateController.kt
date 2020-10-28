package controller

import kotlinx.coroutines.delay
import model.shortstate.ShortGame
import ui.UIMain

object ShortStateController {

    var activeShortGame: ShortGame = ShortGame()

    suspend fun run(){
        println("got here")
        while(true){
            delay(UIMain.frameDelay.toLong())

            val pixelsToMove = 100 * UIMain.frameDelay / 1000
            activeShortGame.characters.forEach {
                if(it.targetX != null){
                    if(it.targetX!! > it.location.x){
                        it.location.x += pixelsToMove
                    }
                    if(it.targetX!! < it.location.x){
                        it.location.x -= pixelsToMove
                    }
                }
            }

            activeShortGame.communications.forEach { it.age++ }
            activeShortGame.communications.removeAll { it.age > 100 }
        }
    }

}