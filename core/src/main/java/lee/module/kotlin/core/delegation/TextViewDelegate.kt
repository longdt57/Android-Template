package lee.module.kotlin.core.delegation

import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun View.delegateTextView(@IdRes id: Int) = object : ReadWriteProperty<Any, CharSequence?> {

    override fun getValue(thisRef: Any, property: KProperty<*>): CharSequence? =
        findViewById<TextView>(id).text

    override fun setValue(thisRef: Any, property: KProperty<*>, value: CharSequence?) {
        findViewById<TextView>(id).text = value
    }
}
