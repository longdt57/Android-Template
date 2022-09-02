package lee.module.kotlin.pattern.data.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

open class BaseSharedPreferences constructor(
    applicationContext: Context,
    prefName: String
) {

    open val sharedPreferences: SharedPreferences by lazy {
        applicationContext.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    }

    inline fun <reified T> argsNullable(
        key: String,
        defaultValue: T? = null
    ): ReadWriteProperty<Any, T?> {
        return object : ReadWriteProperty<Any, T?> {
            override fun getValue(
                thisRef: Any,
                property: KProperty<*>
            ) = sharedPreferences.get(key) ?: defaultValue

            override fun setValue(
                thisRef: Any,
                property: KProperty<*>,
                value: T?
            ) {
                sharedPreferences.set(key, value)
            }
        }
    }

    inline fun <reified T> args(
        key: String,
        defaultValue: T
    ): ReadWriteProperty<Any, T> {
        return object : ReadWriteProperty<Any, T> {
            override fun getValue(
                thisRef: Any,
                property: KProperty<*>
            ) = sharedPreferences.get(key) ?: defaultValue

            override fun setValue(
                thisRef: Any,
                property: KProperty<*>,
                value: T
            ) {
                sharedPreferences.set(key, value)
            }
        }
    }

    fun remove(key: String) {
        sharedPreferences.execute { it.remove(key) }
    }

    fun clearAll() {
        sharedPreferences.execute { it.clear() }
    }
}
