package lee.module.kotlin.core.data.sharedpreferences

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import lee.module.kotlin.core.delegation.getKey

inline fun <reified T> SharedPreferences.argsNullable(
    key: String? = null,
    defaultValue: T? = null
): ReadWriteProperty<Any, T?> {
    return object : ReadWriteProperty<Any, T?> {
        override fun getValue(
            thisRef: Any,
            property: KProperty<*>
        ) = get(property.getKey(key)) ?: defaultValue

        override fun setValue(
            thisRef: Any,
            property: KProperty<*>,
            value: T?
        ) {
            set(property.getKey(key), value)
        }
    }
}

inline fun <reified T> SharedPreferences.args(
    key: String? = null,
    defaultValue: T
): ReadWriteProperty<Any, T> {
    return object : ReadWriteProperty<Any, T> {
        override fun getValue(
            thisRef: Any,
            property: KProperty<*>
        ) = get(property.getKey(key)) ?: defaultValue

        override fun setValue(
            thisRef: Any,
            property: KProperty<*>,
            value: T
        ) {
            set(property.getKey(key), value)
        }
    }
}