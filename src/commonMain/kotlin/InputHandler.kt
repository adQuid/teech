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
        Key.T to { ShortStateController.activeShortGame.communications
                .add(Communication(Coordinate(UIMain.player!!.location.x,UIMain.player!!.location.y))) },
        Key.A to {UIMain.player!!.targetX = UIMain.player!!.location.x - 50; UIMain.player!!.location.x -= 10},
        Key.D to {UIMain.player!!.targetX = UIMain.player!!.location.x + 50}
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