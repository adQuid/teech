package ui.uilayers

import InputHandler
import com.soywiz.korev.Key
import com.soywiz.korev.KeyEvent
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
        Key.A to { event: KeyEvent -> typeChar("A") },
        Key.B to { event: KeyEvent -> typeChar("B") },
        Key.C to { event: KeyEvent -> typeChar("C") },
        Key.D to { event: KeyEvent -> typeChar("D") },
        Key.E to { event: KeyEvent -> typeChar("E") },
        Key.F to { event: KeyEvent -> typeChar("F") },
        Key.G to { event: KeyEvent -> typeChar("G") },
        Key.H to { event: KeyEvent -> typeChar("H") },
        Key.I to { event: KeyEvent -> typeChar("I") },
        Key.J to { event: KeyEvent -> typeChar("J") },
        Key.K to { event: KeyEvent -> typeChar("K") },
        Key.L to { event: KeyEvent -> typeChar("L") },
        Key.M to { event: KeyEvent -> typeChar("M") },
        Key.N to { event: KeyEvent -> typeChar("N") },
        Key.O to { event: KeyEvent -> typeChar("O") },
        Key.P to { event: KeyEvent -> typeChar("P") },
        Key.Q to { event: KeyEvent -> typeChar("Q") },
        Key.R to { event: KeyEvent -> typeChar("R") },
        Key.S to { event: KeyEvent -> typeChar("S") },
        Key.T to { event: KeyEvent -> typeChar("T") },
        Key.U to { event: KeyEvent -> typeChar("U") },
        Key.V to { event: KeyEvent -> typeChar("V") },
        Key.W to { event: KeyEvent -> typeChar("W") },
        Key.X to { event: KeyEvent -> typeChar("X") },
        Key.Y to { event: KeyEvent -> typeChar("Y") },
        Key.Z to { event: KeyEvent -> typeChar("Z") },
        Key.SPACE to { event: KeyEvent -> typeChar(" ", " ") },
        Key.SLASH to { event: KeyEvent -> typeChar("?", "/")},
        Key.PERIOD to { event: KeyEvent -> this.updateText(textInput.text + ".") },
        Key.BACKSPACE to { event: KeyEvent -> if(textInput.text.isNotEmpty()){this.updateText(textInput.text.substring(0..textInput.text.length-2))} },
        Key.ENTER to { event: KeyEvent -> UIMain.player!!.say(textInput.text, UIMain.player!!.getTarget()); UIMain.defocus() }
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

    fun typeChar(character: String){
        typeChar(character.toUpperCase(), character.toLowerCase())
    }

    fun typeChar(upper: String, lower: String){
        if(UIMain.shiftDown){
            updateText(textInput.text + upper)
        } else {
            updateText(textInput.text + lower)
        }
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