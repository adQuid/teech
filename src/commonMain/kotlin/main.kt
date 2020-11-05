import com.soywiz.korio.async.async
import com.soywiz.korio.async.launch
import controller.ShortStateController
import kotlinx.coroutines.GlobalScope
import model.shortstate.ShortGame
import ui.UIMain

fun main(){

	GlobalScope.async{
		ShortStateController.activeShortGame = ShortGame()
		ShortStateController.run()
	}
	GlobalScope.async{
		UIMain.makeUI()
	}

}

