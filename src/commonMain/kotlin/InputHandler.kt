import com.soywiz.korev.Key
import com.soywiz.korev.KeyEvent
import com.soywiz.korev.MouseButton
import com.soywiz.korev.MouseEvent
import com.soywiz.korge.ui.TextButton
import controller.ShortStateController
import model.shortstate.Communication
import model.shortstate.Coordinate
import model.shortstate.ShortGame
import ui.UIMain
import ui.uilayers.DialogMenu

class InputHandler {

    companion object{
        val defaultKeys = mutableMapOf(
        Key.T to { event: KeyEvent -> UIMain.menuOverlays.push(DialogMenu()) },
        Key.A to { event: KeyEvent -> UIMain.player!!.setTargetPosition(Coordinate(UIMain.player!!.location.x - 50, UIMain.player!!.location.y))},
        Key.D to { event: KeyEvent -> UIMain.player!!.setTargetPosition(Coordinate(UIMain.player!!.location.x + 50, UIMain.player!!.location.y))},
        Key.S to { event: KeyEvent -> UIMain.player!!.setTargetPosition(Coordinate(UIMain.player!!.location.x, UIMain.player!!.location.y + 50))},
        Key.W to { event: KeyEvent -> UIMain.player!!.setTargetPosition(Coordinate(UIMain.player!!.location.x, UIMain.player!!.location.y - 50))}
        )

        val defaultMouse = mutableMapOf(
                MouseButton.LEFT to { event: MouseEvent -> UIMain.player!!.target(null) },
                MouseButton.RIGHT to { event: MouseEvent -> UIMain.player!!.targetX = event.x-50; UIMain.player!!.targetY = event.y-50 }
        )
    }

    val keyPressMappings: MutableMap<Key, (event: KeyEvent) -> Any>

    val mouseButtonMappings: MutableMap<MouseButton, (event: MouseEvent) -> Unit>

    constructor(keyPressMappings: MutableMap<Key, (event: KeyEvent) -> Any> = defaultKeys,
                mouseButtonMappings: MutableMap<MouseButton, (event: MouseEvent) -> Unit> = defaultMouse){
        this.keyPressMappings = keyPressMappings
        this.mouseButtonMappings = mouseButtonMappings
    }

    fun handleInput(event: KeyEvent){
        val type = event.key
        if(keyPressMappings.containsKey(type)){
            keyPressMappings[type]!!(event)
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