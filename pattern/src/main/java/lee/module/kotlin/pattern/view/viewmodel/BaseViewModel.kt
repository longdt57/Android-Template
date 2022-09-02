package lee.module.kotlin.pattern.view.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import lee.module.kotlin.pattern.view.model.MessageUiState
import lee.module.kotlin.pattern.view.model.UiState
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseViewModel(app: Application) : AndroidViewModel(app) {

    protected val _uiState = MutableSharedFlow<UiState>(extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val uiState: SharedFlow<UiState> = _uiState

    protected val _loading = MutableSharedFlow<IsLoading>(extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val loading: SharedFlow<IsLoading> = _loading

    fun postUiState(event: UiState) {
        launchInMain {
            _uiState.emit(event)
        }
    }

    protected fun showToast(text: String) {
        launchInMain {
            _uiState.emit(MessageUiState.ToastUiState(text))
        }
    }

    protected fun showLoading() {
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