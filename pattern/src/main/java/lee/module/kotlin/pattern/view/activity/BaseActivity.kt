package lee.module.kotlin.pattern.view.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.Flow
import lee.module.kotlin.pattern.view.ILoading
import lee.module.kotlin.pattern.view.IUiState
import lee.module.kotlin.pattern.view.IView
import lee.module.kotlin.pattern.view.launchCollect
import lee.module.kotlin.pattern.view.model.MessageUiState
import lee.module.kotlin.pattern.view.model.UiState
import lee.module.kotlin.pattern.view.viewmodel.BaseViewModel
import lee.module.kotlin.pattern.view.viewmodel.IsLoading

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity(), IUiState, ILoading, IView {

    protected abstract val viewModel: BaseViewModel

    abstract val bindingInflater: (LayoutInflater) -> VB

    private var _binding: VB? = null

    val binding: VB
        get() = _binding as VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingInflater.invoke(layoutInflater).apply {
            _binding = this
        }

        initViewModel()
        setupView()
        setupViewEvents()
        observeViewModel()
    }

    override fun observeViewModel() {
        viewModel.uiState observe ::handleUiState
        viewModel.loading observe ::bindLoading
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun bindLoading(isLoading: IsLoading) {
        if (isLoading) {
            showLoading()
        } else {
            hideLoading()
        }
    }

    override fun handleUiState(uiState: UiState) {
        when (uiState) {
            is MessageUiState -> uiState.showMessage(this)
        }
    }

    @Deprecated("Use collect with State/Share Flow", ReplaceWith("observe(this@BaseActivity) { action(it) }"))
    protected inline infix fun <T> LiveData<T>.observe(crossinline action: (T) -> Unit) {
        observe(this@BaseActivity) {
            action(it)
        }
    }

    protected inline infix fun <T> Flow<T>.observe(crossinline action: (T) -> Unit) {
        launchCollect(this@BaseActivity) {
            action(it)
        }
    }
}
