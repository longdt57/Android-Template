package lee.module.kotlin.pattern.data.sharedpreferences

import android.content.SharedPreferences

fun SharedPreferences.execute(operation: (SharedPreferences.Editor) -> Unit) {
    with(edit()) {
        operation(this)
        apply()
    }
}
