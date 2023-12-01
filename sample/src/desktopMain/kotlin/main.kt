import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.orca.trulysharedprefs.SharedPrefsFactory
import org.orca.trulysharedprefs.sample.App
import java.util.prefs.Preferences

class App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "TrulySharedPrefs Sample"
    ) {
        val prefs = SharedPrefsFactory(App::class.java)
            .createSharedPrefs()

        App(prefs)
    }
}