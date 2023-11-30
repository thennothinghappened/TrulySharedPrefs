# TrulySharedPrefs
Simple [Kotlin Multiplatform](https://www.jetbrains.com/kotlin-multiplatform/) library providing persistent SharedPreferences for Android & Desktop.

## Usage

### Common:
```kotlin
fun somePreferencesStuff(prefs: SharedPrefs) {
    var counter = prefs.getInt("counter", 0)

    println("Counter is now: $counter")

    counter += 5

    // Automatically calls `apply` for Android, on Desktop this is out of our control mostly.
    prefs.editAsync {
        setInt("counter", counter)
    }
}
```

```
> Counter is now: 5
```

### Android-specific:
```kotlin
val sharedPreferences = context.getSharedPreferences("preferences", MODE_PRIVATE)
val prefs = SharedPrefsFactory(sharedPreferences).createSharedPrefs()

somePreferencesStuff(prefs)
```

### Desktop-specific:
```kotlin
// MainKt is used here, but really we just need any class in our package for a node.
val preferences = Preferences.userNodeForPackage(MainKt::class.java)
val prefs = SharedPrefsFactory(preferences).createSharedPrefs()

somePreferencesStuff(prefs)
```

## Sample
See the `:sample` module for a usage in Desktop and Android [Compose](https://www.jetbrains.com/lp/compose-multiplatform/) as an example
settings menu.

## TODO
Intending to add a module that makes it easier to work with Compose, i.e. bringing into the
`remember` system.