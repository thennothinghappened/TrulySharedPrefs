package org.orca.trulysharedprefs.sample

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.orca.trulysharedprefs.SharedPrefs

object PrefsSchema {
    const val EXAMPLE_STRING_KEY = "example_string"
    val EXAMPLE_STRING_DEF: String? = null
    const val EXAMPLE_STRING_NAME = "Example String"

    const val EXAMPLE_BOOLEAN_KEY = "example_boolean"
    const val EXAMPLE_BOOLEAN_DEF = true
    const val EXAMPLE_BOOLEAN_NAME = "Example Boolean"
}

@Composable
fun App(prefs: SharedPrefs) {

    var exampleString by remember { mutableStateOf(prefs.getString(PrefsSchema.EXAMPLE_STRING_KEY, PrefsSchema.EXAMPLE_STRING_DEF)) }
    var exampleBoolean by remember { mutableStateOf(prefs.getBoolean(PrefsSchema.EXAMPLE_BOOLEAN_KEY, PrefsSchema.EXAMPLE_BOOLEAN_DEF)) }

    var changed by remember { mutableStateOf(false) }

    fun save() {
        prefs.editSync {
            putString(PrefsSchema.EXAMPLE_STRING_KEY, exampleString)
            putBoolean(PrefsSchema.EXAMPLE_BOOLEAN_KEY, exampleBoolean)
        }

        changed = false
    }

    Surface(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column {
            Text("Settings", style = MaterialTheme.typography.titleMedium)

            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StringSetting(
                    name = PrefsSchema.EXAMPLE_STRING_NAME,
                    value = exampleString.toString(),
                    onChange = {
                        exampleString = it
                        changed = true
                    }
                )

                BoolSetting(
                    name = PrefsSchema.EXAMPLE_BOOLEAN_NAME,
                    value = exampleBoolean,
                    onChange = {
                        exampleBoolean = it
                        changed = true
                    }
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = ::save,
                    enabled = changed
                ) {
                    Text("Save")
                }

                Button(
                    onClick = {
                        exampleString = PrefsSchema.EXAMPLE_STRING_DEF
                        exampleBoolean = PrefsSchema.EXAMPLE_BOOLEAN_DEF

                        save()
                    }
                ) {
                    Text("Reset")
                }
            }
        }
    }
}

@Composable
fun StringSetting(
    name: String,
    value: String,
    onChange: (value: String) -> Unit
) = Setting(
    name
) {
    TextField(
        value = value,
        onValueChange = onChange
    )
}

@Composable
fun BoolSetting(
    name: String,
    value: Boolean,
    onChange: (value: Boolean) -> Unit
) = Setting(
    name
) {
    Switch(
        checked = value,
        onCheckedChange = onChange
    )
}

@Composable
fun Setting(
    name: String,
    content: @Composable () -> Unit
) {
    Card(Modifier.fillMaxWidth()) {
        Row(Modifier.padding(8.dp)) {
            Text(
                text = name,
                modifier = Modifier.align(Alignment.CenterVertically),
                style = MaterialTheme.typography.labelLarge,
            )

            Spacer(Modifier.weight(1f))

            content()
        }
    }
}