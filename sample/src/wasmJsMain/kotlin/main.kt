
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import kotlinx.browser.window
import org.orca.trulysharedprefs.SharedPrefsFactory
import org.orca.trulysharedprefs.sample.App

private val prefs = SharedPrefsFactory().createSharedPrefs()

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        App(prefs)
    }
}
