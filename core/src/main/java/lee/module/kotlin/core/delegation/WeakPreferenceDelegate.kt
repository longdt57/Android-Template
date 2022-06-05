package lee.module.kotlin.core.delegation

import java.lang.ref.WeakReference
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <T : Any> weakReference(value: T? = null): ReadWriteProperty<Any, T?> =
    object : ReadWriteProperty<Any, T?> {
        private var reference: WeakReference<T> = WeakReference(value)

        override fun getValue(thisRef: Any, property: KProperty<*>): T? {
            return reference.get()
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
            reference = WeakReference(value)
        }
    }
