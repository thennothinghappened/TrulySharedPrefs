import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.orca.trulysharedprefs.SharedPrefsFactory
import org.orca.trulysharedprefs.sample.App
import java.util.prefs.Preferences

class App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "TrulySharedPrefs Sample"
    ) {
        val preferences = Preferences.userNodeForPackage(App::class.java)
        val prefs = SharedPrefsFactory(preferences).createSharedPrefs()

        App(prefs)
    }
}