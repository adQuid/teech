package ui

import InputHandler
import com.soywiz.korge.view.*

abstract class UILayer {

    abstract val inputHandler: InputHandler

    abstract fun views(): Set<View>

}