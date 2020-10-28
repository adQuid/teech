import com.soywiz.korio.async.async
import com.soywiz.korio.async.launch
import controller.ShortStateController
import kotlinx.coroutines.GlobalScope
import ui.UIMain

fun main(){

	GlobalScope.async{
		ShortStateController.run()
	}
	GlobalScope.async{
		UIMain.makeUI()
	}

}

