import com.soywiz.korev.Key
import com.soywiz.korev.MouseButton
import com.soywiz.korev.MouseEvent
import model.shortstate.Communication
import model.shortstate.Coordinate
import model.shortstate.ShortGame

object InputHandler {

    val keyPressMappings = mutableMapOf(
        Key.T to { ShortGame.communications.add(Communication(Coordinate(ShortGame.characters[0].location.x,ShortGame.characters[0].location.y))) }
    )

    val mouseButtonMappings = mutableMapOf(
        MouseButton.LEFT to { event: MouseEvent -> ShortGame.characters[0]!!.targetX = event.x },
        MouseButton.RIGHT to { event: MouseEvent -> ShortGame.characters[0]!!.targetY = event.y }
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