package lee.module.kotlin.pattern.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import lee.module.kotlin.pattern.domain.UseCaseResult
import lee.module.kotlin.pattern.livedata.EventLiveData
import lee.module.kotlin.pattern.view.model.UiState

abstract class BaseViewModel : ViewModel() {

    protected val _uiState: MutableLiveData<UiState> = EventLiveData<UiState>()
    val uiState: LiveData<UiState> = _uiState

    private val _showLoading = EventLiveData<IsLoading>()
    val showLoading: LiveData<IsLoading>
        get() = _showLoading

    fun <T> UseCaseResult<T>.onResult(callback: (T) -> Unit): UseCaseResult<T> {
        if (this is UseCaseResult.Success) {
            execute(Dispatchers.Main) {
                callback.invoke(data)
            }
        }
        return this
    }

    fun <T> UseCaseResult<T>.onError(callback: (Throwable) -> Unit): UseCaseResult<T> {
        if (this is UseCaseResult.Error) {
            execute(Dispatchers.Main) {
                callback.invoke(exception)
            }
        }
        return this
    }

    protected fun execute(
        coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
        job: suspend () -> Unit
    ) =
        viewModelScope.launch(coroutineDispatcher) {
            job.invoke()
        }
}
