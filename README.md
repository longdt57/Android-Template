# kotlin-ext
[![Generic badge](https://img.shields.io/badge/Version-1.0.4-<green>.svg)](https://github.com/longdt57/kotlin-ext/releases/latest)

Extension, Delegation, and more...

## Delegation & Extension

### Implementation
build.gradle
```
repositories {
  maven { url "https://jitpack.io" }
}

dependencies {
  implementation 'com.github.longdt57.kotlin-ext:core:{version}'
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

## Pattern
[Docs HERE](https://github.com/longdt57/kotlin-ext/tree/develop/pattern)
