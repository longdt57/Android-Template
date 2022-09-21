package lee.module.design.base.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.CallSuper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.Flow
import lee.module.design.base.viewmodel.BaseViewModel

abstract class BaseComposeActivity : ComponentActivity(), BaseComposeCallbacks {

    private val toaster: Toaster by lazy { Toaster(this) }

    protected abstract val composeScreen: @Composable () -> Unit

    protected open val viewModel: BaseViewModel by viewModels()

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        initView()
        bindViewModel()
    }

    private fun initView() {
        setContent {
                composeScreen.invoke()
                LoadingScreen(isLoading = viewModel.loading.collectAsState().value)
            }
    }

    override fun bindViewModel() {
        viewModel.error bindTo { toaster.display(it) }
    }

    protected inline infix fun <T> Flow<T>.bindTo(crossinline action: (T) -> Unit) {
        launchCollect(this@BaseComposeActivity) {
            action(it)
        }
    }
}
