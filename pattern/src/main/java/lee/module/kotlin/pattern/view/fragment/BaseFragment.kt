package lee.module.kotlin.pattern.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
import lee.module.kotlin.pattern.view.hideSoftKeyboard
import lee.module.kotlin.pattern.view.viewmodel.BaseViewModel
import lee.module.kotlin.pattern.view.viewmodel.IsLoading

abstract class BaseFragment<VB : ViewBinding> : Fragment(), IUiStateController, ILoading, IView {

    protected abstract val viewModel: BaseViewModel

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    private var _binding: VB? = null

    val binding: VB
        get() = _binding as VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return bindingInflater.invoke(inflater, container, false).apply {
            _binding = this
        }.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewEvents()
        observeViewModel()
    }

    override fun observeViewModel() {
        viewModel.uiState observe { uiStateController.invoke(it) }
        viewModel.showLoading observe ::bindLoading
    }

    override fun setupViewEvents() {
        requireNotNull(view).setOnClickListener {
            requireActivity().hideSoftKeyboard()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
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
        observe(viewLifecycleOwner) {
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
