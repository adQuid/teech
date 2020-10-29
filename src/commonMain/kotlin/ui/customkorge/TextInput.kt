package ui.customkorge

import com.soywiz.korge.html.Html
import com.soywiz.korge.input.mouse
import com.soywiz.korge.ui.*
import com.soywiz.korge.view.ninePatch
import com.soywiz.korge.view.position
import com.soywiz.korge.view.setText
import com.soywiz.korge.view.text
import com.soywiz.korma.geom.Rectangle

class TextInput(
        width: Double = 128.0,
        height: Double = 64.0,
        text: String = "",
        skin: UISkin = DefaultUISkin,
        textFont: Html.FontFace = DefaultUIFont
) : UIView(width, height){

    protected open val rect = ninePatch(skin.normal, width, height, 10.0 / 64.0, 10.0 / 64.0, 54.0 / 64.0, 54.0 / 64.0)
    var text by uiObservable(text) {  }
    private val textView = text(text)

    init {
        updateText(text)
    }

    fun updateText(text: String) {
        this.text = text
        textView.setTextBounds(Rectangle(10, height * 0.45, width, height))
        textView.setText(text)
        if(text.contains("Sweet")){
            println("bah bah bah")
        }
    }

}