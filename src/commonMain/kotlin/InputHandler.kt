import com.soywiz.korev.Key
import com.soywiz.korev.MouseButton
import com.soywiz.korev.MouseEvent
import controller.ShortStateController
import model.shortstate.Communication
import model.shortstate.Coordinate
import model.shortstate.ShortGame
import ui.UIMain

object InputHandler {

    val keyPressMappings = mutableMapOf(
        Key.T to { UIMain.player!!.say("Sweet Caroline") },
        Key.A to {UIMain.player!!.targetX = UIMain.player!!.location.x - 50},
        Key.D to {UIMain.player!!.targetX = UIMain.player!!.location.x + 50},
        Key.S to {UIMain.player!!.targetY = UIMain.player!!.location.y + 50},
        Key.W to {UIMain.player!!.targetY = UIMain.player!!.location.y - 50}
    )

    val mouseButtonMappings = mutableMapOf(
        MouseButton.LEFT to { event: MouseEvent ->  },
        MouseButton.RIGHT to { event: MouseEvent -> UIMain.player!!.targetX = event.x; UIMain.player!!.targetY = event.y }
    )

    fun handleInput(type: Key){
        if(keyPressMappings.containsKey(type)){
            keyPressMappings[type]!!()
        } else {
            println("unbound button pressed: ${type}")
        }
    }

    fun handleInput(event: MouseEvent){
        if(mouseButtonMappings.containsKey(event.button)){
            mouseButtonMappings[event.button]!!(event)
        }
    }

}