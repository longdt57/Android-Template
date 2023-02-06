package lee.module.kotlin.core.extension

import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import lee.module.kotlin.core.CoreApplication

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

fun ComponentActivity.showDebugToast(message: String) {
    if ((applicationContext as? CoreApplication)?.isDebug.orFalse()) {
        lifecycleScope.launch(Dispatchers.Main) {
            Toast.makeText(this@showDebugToast.applicationContext, message, Toast.LENGTH_SHORT).show()
        }
    }
}
