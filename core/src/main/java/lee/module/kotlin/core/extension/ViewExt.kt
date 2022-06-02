package lee.module.kotlin.core.extension

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle

fun View.setOnSingleClickListener(action: (View) -> Unit) {
    setOnClickListener { view ->
        view.isClickable = false
        action(view)
        view.postDelayed(
            {
                view.isClickable = true
            },
            300L
        )
    }
}

fun AppCompatActivity.isInForeGround(): Boolean {
    return lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)
}

fun Fragment.isInForeGround(): Boolean {
    return lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)
}
