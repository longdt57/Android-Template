package lee.module.design.base.presenter.view

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> Flow<T>.launchCollect(lifecycleOwner: LifecycleOwner, state: Lifecycle.State = Lifecycle.State.CREATED, collector: (T) -> Unit): Job {
    return lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.lifecycle.repeatOnLifecycle(state) {
            collect { collector.invoke(it) }
        }
    }
}
