package lee.module.design.base.presenter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import lee.module.design.base.presenter.model.Screen
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseViewModel : ViewModel() {

    protected val _navigator = MutableSharedFlow<Screen>()
    val navigator: SharedFlow<Screen>
        get() = _navigator.asSharedFlow()

    protected val _loading = MutableStateFlow(false)
    val loading: StateFlow<IsLoading> get() = _loading.asStateFlow()

    protected val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String>
        get() = _error.asSharedFlow()

    fun navigateTo(event: Screen) {
        launchInMain {
            _navigator.emit(event)
        }
    }

    protected fun showError(text: String) {
        launchInMain {
            _error.emit(text)
        }
    }

    fun showLoading() {
        launchInMain {
            _loading.emit(true)
        }
    }

    protected fun hideLoading() {
        launchInMain {
            _loading.emit(false)
        }
    }

    protected fun <T> Flow<T>.addLoadingObserver(): Flow<T> {
        onStart { showLoading() }
        onCompletion { hideLoading() }
        return this
    }

}

fun ViewModel.launch(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit,
) = viewModelScope.launch(context, start, block)

fun ViewModel.launchInIO(block: suspend CoroutineScope.() -> Unit) = launch(Dispatchers.IO, block = block)

fun ViewModel.launchInMain(block: suspend CoroutineScope.() -> Unit) = launch(Dispatchers.Main, block = block)
