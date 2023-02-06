package lee.module.design.base.presenter.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import kotlinx.coroutines.flow.Flow
import lee.module.design.base.presenter.viewmodel.BaseViewModel

abstract class BaseComposeFragment : Fragment(), BaseComposeCallbacks {

    private val toaster: Toaster by lazy { Toaster(requireContext()) }

    protected abstract val composeScreen: @Composable () -> Unit

    protected open val viewModel: BaseViewModel by viewModels()

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                composeScreen.invoke()
                LoadingScreen(isLoading = viewModel.loading.collectAsState().value)
            }
        }
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
    }

    override fun bindViewModel() {
        viewModel.error bindTo { toaster.display(it) }
    }

    protected inline infix fun <T> Flow<T>.bindTo(crossinline action: (T) -> Unit) {
        launchCollect(viewLifecycleOwner) {
            action(it)
        }
    }
}
