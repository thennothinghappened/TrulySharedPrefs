package org.orca.trulysharedprefs.sample.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.orca.trulysharedprefs.SharedPrefsFactory
import org.orca.trulysharedprefs.sample.App

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE)
        val prefs = SharedPrefsFactory(sharedPreferences).createSharedPrefs()

        setContent {
            App(prefs)
        }
    }
}