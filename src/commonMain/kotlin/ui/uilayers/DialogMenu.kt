package ui.uilayers

import InputHandler
import com.soywiz.korev.Key
import com.soywiz.korge.ui.TextButton
import com.soywiz.korge.view.View
import ui.UILayer
import ui.UIMain
import ui.customkorge.TextInput

class DialogMenu: UILayer {

    override val inputHandler = InputHandler(keyPressMappings = mutableMapOf(
        Key.A to { this.textInput.updateText(textInput.text + "A") },
        Key.B to { this.textInput.updateText(textInput.text + "B") },
        Key.C to { this.textInput.updateText(textInput.text + "C") },
        Key.D to { this.textInput.updateText(textInput.text + "D") },
        Key.E to { this.textInput.updateText(textInput.text + "E") },
        Key.F to { this.textInput.updateText(textInput.text + "F") },
        Key.G to { this.textInput.updateText(textInput.text + "G") },
        Key.H to { this.textInput.updateText(textInput.text + "H") },
        Key.I to { this.textInput.updateText(textInput.text + "I") },
        Key.J to { this.textInput.updateText(textInput.text + "J") },
        Key.K to { this.textInput.updateText(textInput.text + "K") },
        Key.L to { this.textInput.updateText(textInput.text + "L") },
        Key.M to { this.textInput.updateText(textInput.text + "M") },
        Key.N to { this.textInput.updateText(textInput.text + "N") },
        Key.O to { this.textInput.updateText(textInput.text + "O") },
        Key.P to { this.textInput.updateText(textInput.text + "P") },
        Key.Q to { this.textInput.updateText(textInput.text + "Q") },
        Key.R to { this.textInput.updateText(textInput.text + "R") },
        Key.S to { this.textInput.updateText(textInput.text + "S") },
        Key.T to { this.textInput.updateText(textInput.text + "T") },
        Key.U to { this.textInput.updateText(textInput.text + "U") },
        Key.V to { this.textInput.updateText(textInput.text + "V") },
        Key.W to { this.textInput.updateText(textInput.text + "W") },
        Key.X to { this.textInput.updateText(textInput.text + "X") },
        Key.Y to { this.textInput.updateText(textInput.text + "Y") },
        Key.Z to { this.textInput.updateText(textInput.text + "Z") },
        Key.SPACE to { this.textInput.updateText(textInput.text + " ") },
        Key.BACKSPACE to { this.textInput.updateText(textInput.text.substring(0..textInput.text.length-2)) },
        Key.ENTER to { UIMain.player!!.say(textInput.text); UIMain.defocus() }
    ))
    val textInput = TextInput(width = UIMain.width.toDouble())

    constructor(): super(){
        textInput.y = UIMain.height - textInput.height
        textInput.width = UIMain.width.toDouble()
    }

    val views = setOf(
            textInput
            //TextButton(256.0, 32.0, "Enabled Button")
    )

    override fun views(): Set<View> {
        return views
    }

}