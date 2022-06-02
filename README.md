# kotlin-ext
Extension, Delegation, and more...

## Delegation
### Usage
```
// Fragment
class Fragment {
    var title: String by args(defaultValue = "") // use argsNullable for Nullable
}

// SharedPreferences
val preference: SharedPreferences...
var title: String by preference.args(defaultValue = "") // use argsNullable for Nullable

// WeakReference
var title: String? by weakReference(null)
```

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

### Explanations
#### Fragment Arguments
`var title: String by args(defaultValue = "") // use argsNullable for Nullable`
- Normal
```
companion object {
    fun newInstance(title: String): CustomFragment().apply {
        this.title = title
    }
}

var title: String
    set(value) {
        if (arguments == null) arguments == Bundle()
        arguments?.put("ARGS_TITLE", "Hello World!")
    }
    get() = arguments?.get("ARGS_TITLE").orEmpty()
```

#### SharedPreferences
`var title: String by preference.args(defaultValue = "") // use argsNullable for Nullable`

- Normal
```
var title: String
    set(value) {
        preference.put("TITLE", value)
    }
    get() = preference.get("Title").orEmpty()
```

#### WeakReference
`var title: String? by weakReference("Hello World!)`
- Normal
```
var titleReference: WeakReference<String> = WeakReference("Hello World!")
```

###
