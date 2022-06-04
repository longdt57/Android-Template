package lee.module.kotlin.core.delegation

import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun View.delegateTextView(@IdRes id: Int) = findViewById<TextView>(id).delegate()

private fun TextView.delegate() = object : ReadWriteProperty<Any, CharSequence?> {
    override fun getValue(thisRef: Any, property: KProperty<*>): CharSequence? = text

    override fun setValue(thisRef: Any, property: KProperty<*>, value: CharSequence?) {
        text = value
    }
}
