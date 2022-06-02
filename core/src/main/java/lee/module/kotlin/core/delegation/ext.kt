package lee.module.kotlin.core.delegation

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable
import kotlin.reflect.KProperty

fun KProperty<*>.getKey(key: String?): String {
    return key ?: name
}

internal fun <T> Bundle.put(key: String, value: T) {
    when (value) {
        is Boolean -> putBoolean(key, value)
        is String -> putString(key, value)
        is Int -> putInt(key, value)
        is Short -> putShort(key, value)
        is Long -> putLong(key, value)
        is Byte -> putByte(key, value)
        is ByteArray -> putByteArray(key, value)
        is Char -> putChar(key, value)
        is CharArray -> putCharArray(key, value)
        is CharSequence -> putCharSequence(key, value)
        is Float -> putFloat(key, value)
        is Bundle -> putBundle(key, value)
        is Parcelable -> putParcelable(key, value)
        is Serializable -> putSerializable(key, value)
        else -> throw IllegalStateException("Type of property $key is not supported")
    }
}

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
        }
    }
}
