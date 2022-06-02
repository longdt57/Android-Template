package lee.module.kotlin.core.delegation

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

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