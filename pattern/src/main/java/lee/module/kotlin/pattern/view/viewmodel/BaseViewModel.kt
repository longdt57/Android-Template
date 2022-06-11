package lee.module.kotlin.pattern.view.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import lee.module.kotlin.pattern.domain.UseCaseResult
import lee.module.kotlin.pattern.view.model.UiState
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseViewModel(app: Application) : AndroidViewModel(app) {

    protected val _uiState = MutableSharedFlow<UiState>()
    val uiState: SharedFlow<UiState> = _uiState

    private val _showLoading = MutableSharedFlow<IsLoading>()
    val showLoading: SharedFlow<IsLoading> = _showLoading

    fun navigateTo(event: UiState) {
        launchInMain {
            _uiState.emit(event)
        }
    }

    protected fun showLoading() {
        launchInMain {
            _showLoading.emit(true)
        }
    }

    protected fun hideLoading() {
        launchInMain {
            _showLoading.emit(false)
        }
    }

    fun <T> UseCaseResult<T>.onSuccess(callback: (T) -> Unit): UseCaseResult<T> {
        if (this is UseCaseResult.Success) {
            launchInMain {
                callback.invoke(data)
            }
        }
        return this
    }

    fun <T> UseCaseResult<T>.onError(callback: (Throwable) -> Unit): UseCaseResult<T> {
        if (this is UseCaseResult.Error) {
            launchInMain {
                callback.invoke(exception)
            }
        }
        return this
    }

}

internal fun ViewModel.launch(
    context: CoroutineContext = EmptyCoroutineContext,
    job: suspend () -> Unit
) = viewModelScope.launch(context) {
    job.invoke()
}

internal fun ViewModel.launchInMain(job: suspend () -> Unit) = launch(Dispatchers.Main, job)

