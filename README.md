# kotlin-ext
[![Version](https://jitpack.io/v/longdt57/kotlin-ext.svg)](https://github.com/longdt57/kotlin-ext/releases)

- Delegation & Extension
- [Pattern Page HERE](https://github.com/longdt57/kotlin-ext/tree/develop/pattern)



## Delegation & Extension

### Implementation
build.gradle
```
repositories {
  maven { url "https://jitpack.io" }
}

dependencies {
  implementation 'com.github.longdt57:kotlin-ext:{version}'
}
```

### Delegation
```
// Fragment
class Fragment {
    var title: String by args(defaultValue = "") // use argsNullable for Nullable
}

// SharedPreferences
var title: String by sharedPreference.args(defaultValue = "") // use argsNullable for Nullable

// WeakReference
var title: String? by weakReference(null)

// TextView
class CustomView : ... {
    var title: String by delegateTextView(R.id.title)
}

```

### Extension
```
- Boolean?.orFalse()
- Bundle.put(key, value)
- Float.dpToPx(context)
- View.setOnSingleClickListener { view -> }

- Context.dpToPx(value)
- Context.openSettings()
- Context.shareText(text)
- Context.callTo(phone)
- Context.sendEmail(email)
- Context.openPlayStore(pkg)
- Context.gotoGMap(address)
```
