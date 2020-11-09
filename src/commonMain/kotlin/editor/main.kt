package editor

import com.soywiz.korge.Korge
import com.soywiz.korge.view.position
import com.soywiz.korim.color.Colors
import model.shortstate.Encounter
import ui.UIMain


suspend fun openEditor() = Korge(width = UIMain.width, height = UIMain.height, bgcolor = Colors["#2b2b2b"]) {

    var encounter = Encounter()

    encounter.spawns.map { it.generateCharacter() }.map{
        val display = it.display()
        display.position(it.location.x, it.location.y)
        display.scale = 0.8
        addChild(display)
    }
}