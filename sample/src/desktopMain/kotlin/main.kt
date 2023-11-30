import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.orca.trulysharedprefs.sample.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "TrulySharedPrefs Sample"
    ) {
        App()
    }
}