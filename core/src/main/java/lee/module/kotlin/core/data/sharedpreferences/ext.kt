package lee.module.kotlin.core.data.sharedpreferences

import android.content.SharedPreferences
import android.os.Parcelable
import com.google.gson.Gson

internal fun SharedPreferences.execute(operation: (SharedPreferences.Editor) -> Unit) {
    with(edit()) {
        operation(this)
        apply()
    }
}

inline fun <reified T> SharedPreferences.get(key: String): T? =
    if (this.contains(key)) {
        when (T::class) {
            Boolean::class -> this.getBoolean(key, false) as T?
            String::class -> this.getString(key, null) as T?
            Float::class -> this.getFloat(key, 0f) as T?
            Int::class -> this.getInt(key, 0) as T?
            Long::class -> this.getLong(key, 0L) as T?
            Parcelable::class -> this.getString(key, null).takeUnless { it.isNullOrBlank() }?.let {
                Gson().fromJson(it, T::class.java) ?: null
            }
            else -> null
        }
    } else {
        null
    }

fun <T> SharedPreferences.set(key: String, value: T) {
    this.execute {
        when (value) {
            is Boolean -> it.putBoolean(key, value)
            is String -> it.putString(key, value)
            is Float -> it.putFloat(key, value)
            is Long -> it.putLong(key, value)
            is Int -> it.putInt(key, value)
            is Parcelable -> it.putString(key, Gson().toJson(value))
        }
    }
}
