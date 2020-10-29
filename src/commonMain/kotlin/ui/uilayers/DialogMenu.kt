package ui.uilayers

import com.soywiz.korge.ui.TextButton
import com.soywiz.korge.view.View
import ui.UILayer

class DialogMenu: UILayer() {

    val views = setOf(
            TextButton(256.0, 32.0, "Enabled Button")
    )

    override fun views(): Set<View> {
        return views
    }

}