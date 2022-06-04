package lee.module.kotlin.pattern.view.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import lee.module.kotlin.pattern.domain.UseCaseResult
import lee.module.kotlin.pattern.livedata.EventLiveData
import lee.module.kotlin.pattern.view.model.NavigationEvent

abstract class BaseViewModel(app: Application) : AndroidViewModel(app) {

    protected val _navigator = EventLiveData<NavigationEvent>()
    val navigator: LiveData<NavigationEvent> = _navigator

    private val _showLoading = EventLiveData<IsLoading>()
    val showLoading: LiveData<IsLoading>
        get() = _showLoading

    fun navigateTo(event: NavigationEvent) {
        launchInMain {
            _navigator.value = event
        }
    }

    protected fun showLoading() {
        launchInMain {
            _showLoading.value = true
        }
    }

    protected fun hideLoading() {
        launchInMain {
            _showLoading.value = false
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
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
    job: suspend () -> Unit
) = viewModelScope.launch(coroutineDispatcher) {
    job.invoke()
}

internal fun ViewModel.launchInMain(job: suspend () -> Unit) = launch(Dispatchers.Main, job)

