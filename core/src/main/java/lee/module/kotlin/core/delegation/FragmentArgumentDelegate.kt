package lee.module.kotlin.core.delegation

import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

internal class FragmentArgumentDelegate<T : Any?>(
    private val key: String? = null,
    private val defaultValue: T
) : ReadWriteProperty<Fragment, T> {

    @Suppress("UNCHECKED_CAST")
    override fun getValue(
        thisRef: Fragment,
        property: KProperty<*>
    ): T {
        val key = property.getKey(this.key)
        return thisRef.arguments?.get(key) as? T ?: defaultValue
    }

    override fun setValue(
        thisRef: Fragment,
        property: KProperty<*>,
        value: T
    ) {
        val args = thisRef.arguments
            ?: Bundle().also(thisRef::setArguments)
        val key = property.getKey(this.key)
        value?.let { args.put(key, it) } ?: args.remove(key)
    }
}

fun <T : Any> args(key: String? = null, defaultValue: T): ReadWriteProperty<Fragment, T> =
    FragmentArgumentDelegate(key, defaultValue)
