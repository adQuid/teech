import com.soywiz.korio.async.launch
import kotlinx.coroutines.GlobalScope
import ui.UIMain

fun main(){

	GlobalScope.launch{
		UIMain.makeUI()
	}

}

