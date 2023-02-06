package lee.module.kotlin.sample.deeplink

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.airbnb.deeplinkdispatch.DeepLinkHandler
import dagger.hilt.android.AndroidEntryPoint

@DeepLinkHandler(AppDeepLinkModule::class)
@AndroidEntryPoint
class DeepLinkActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val deepLinkDelegate = DeepLinkDelegate(
            AppDeepLinkModuleRegistry(),
        )
        deepLinkDelegate.dispatchFrom(this)
        finish()
    }
}
