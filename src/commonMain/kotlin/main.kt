import com.soywiz.korge.Korge
import com.soywiz.korim.color.Colors
import com.soywiz.korio.async.async
import com.soywiz.korio.async.launch
import com.soywiz.korio.lang.printStackTrace
import controller.ShortStateController
import editor.openEditor
import kotlinx.coroutines.GlobalScope
import model.shortstate.Encounter
import model.shortstate.ShortGame
import serialize.Serializer
import ui.UIMain

const val editorMode = false

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

