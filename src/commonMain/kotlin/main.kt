import com.soywiz.korge.Korge
import com.soywiz.korim.color.Colors
import com.soywiz.korio.async.async
import com.soywiz.korio.async.launch
import controller.ShortStateController
import editor.openEditor
import kotlinx.coroutines.GlobalScope
import model.shortstate.Encounter
import model.shortstate.ShortGame
import ui.UIMain

const val editorMode = true

fun main(){

	if(editorMode){
		GlobalScope.async{
			openEditor()
		}
	}else {

		GlobalScope.async{
			ShortStateController.activeShortGame = ShortGame(Encounter())
			ShortStateController.run()
		}
		GlobalScope.async{
			UIMain.makeUI()
		}
	}
}

