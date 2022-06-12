package lee.module.kotlin.pattern.view.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import lee.module.kotlin.pattern.view.ILoading
import lee.module.kotlin.pattern.view.IUiStateController
import lee.module.kotlin.pattern.view.IView
import lee.module.kotlin.pattern.view.viewmodel.BaseViewModel
import lee.module.kotlin.pattern.view.viewmodel.IsLoading

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity(), IUiStateController, ILoading, IView {

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
        viewModel.uiState observe { uiStateController.invoke(it) }
        viewModel.showLoading observe ::bindLoading
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

    @Deprecated("Use collect with State/Share Flow")
    protected inline infix fun <T> LiveData<T>.observe(crossinline action: (T) -> Unit) {
        observe(this@BaseActivity) {
            action(it)
        }
    }

    protected inline infix fun <T> Flow<T>.observe(crossinline action: (T) -> Unit) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                collect {
                    action.invoke(it)
                }
            }
        }
    }
}
