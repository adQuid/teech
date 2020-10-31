package ui.uilayers

import InputHandler
import com.soywiz.korev.Key
import com.soywiz.korge.ui.TextButton
import com.soywiz.korge.view.*
import com.soywiz.korim.color.RGBA
import com.soywiz.korma.geom.Rectangle
import model.shortstate.Message
import model.shortstate.MessageFactory
import ui.UILayer
import ui.UIMain
import ui.customkorge.TextInput

class DialogMenu: UILayer {

    override val inputHandler = InputHandler(keyPressMappings = mutableMapOf(
        Key.A to { this.updateText(textInput.text + "A") },
        Key.B to { this.updateText(textInput.text + "B") },
        Key.C to { this.updateText(textInput.text + "C") },
        Key.D to { this.updateText(textInput.text + "D") },
        Key.E to { this.updateText(textInput.text + "E") },
        Key.F to { this.updateText(textInput.text + "F") },
        Key.G to { this.updateText(textInput.text + "G") },
        Key.H to { this.updateText(textInput.text + "H") },
        Key.I to { this.updateText(textInput.text + "I") },
        Key.J to { this.updateText(textInput.text + "J") },
        Key.K to { this.updateText(textInput.text + "K") },
        Key.L to { this.updateText(textInput.text + "L") },
        Key.M to { this.updateText(textInput.text + "M") },
        Key.N to { this.updateText(textInput.text + "N") },
        Key.O to { this.updateText(textInput.text + "O") },
        Key.P to { this.updateText(textInput.text + "P") },
        Key.Q to { this.updateText(textInput.text + "Q") },
        Key.R to { this.updateText(textInput.text + "R") },
        Key.S to { this.updateText(textInput.text + "S") },
        Key.T to { this.updateText(textInput.text + "T") },
        Key.U to { this.updateText(textInput.text + "U") },
        Key.V to { this.updateText(textInput.text + "V") },
        Key.W to { this.updateText(textInput.text + "W") },
        Key.X to { this.updateText(textInput.text + "X") },
        Key.Y to { this.updateText(textInput.text + "Y") },
        Key.Z to { this.updateText(textInput.text + "Z") },
        Key.SPACE to { this.updateText(textInput.text + " ") },
        Key.BACKSPACE to { if(textInput.text.isNotEmpty()){this.updateText(textInput.text.substring(0..textInput.text.length-2))} },
        Key.ENTER to { UIMain.player!!.say(textInput.text); UIMain.defocus() }
    ))
    val textInput = TextInput(width = UIMain.width * 0.8)

    var dialogTags = listOf<View>()

    constructor(): super(){
        textInput.y = UIMain.height - textInput.height
        textInput.width = UIMain.width.toDouble()
    }

    var views = setOf(
        textInput
    ).plus(dialogTags.toSet())

    override fun views(): Set<View> {
        return views
    }

    fun updateText(text: String){
        dialogTags = Message.messageListFromString(text).mapIndexed{
            index: Int, s: Message ->
            val retval = RoundRect(350.0, 50.0, 5.0)
            retval.color = RGBA.Companion.float(0.0, 0.8, 0.0, 1.0)
            retval.y = UIMain.height - ((index+1) * 50.0) - textInput.height
            val textLabel = Text(s.tooltip()).centerOn(retval)
            textLabel.x = retval.x + 5
            textLabel.y = retval.y + retval.height/3

            listOf(retval, textLabel)
        }.flatten()
        this.textInput.updateText(text)

        views = setOf(
                textInput
        ).plus(dialogTags.toSet())
    }

}