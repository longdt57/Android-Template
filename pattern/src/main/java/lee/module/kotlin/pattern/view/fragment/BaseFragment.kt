package lee.module.kotlin.pattern.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.viewbinding.ViewBinding
import lee.module.kotlin.pattern.view.ILoading
import lee.module.kotlin.pattern.view.IState
import lee.module.kotlin.pattern.view.IView
import lee.module.kotlin.pattern.view.hideSoftKeyboard
import lee.module.kotlin.pattern.view.viewmodel.BaseViewModel
import lee.module.kotlin.pattern.view.viewmodel.IsLoading

abstract class BaseFragment<VB : ViewBinding> : Fragment(), IState, ILoading,
    IView {

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
        bindViewEvents()
        bindViewModel()
    }

    override fun initViewModel() {
        viewModel.uiState observe { uiNavigator.invoke(it) }
        viewModel.showLoading observe ::bindLoading
    }

    override fun bindViewEvents() {
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

    protected inline infix fun <T> LiveData<T>.observe(crossinline action: (T) -> Unit) {
        observe(viewLifecycleOwner) {
            action(it)
        }
    }
}
