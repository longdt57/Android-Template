package lee.module.kotlin.sample

import dagger.hilt.android.HiltAndroidApp
import lee.module.kotlin.core.CoreApplication

@HiltAndroidApp
class SampleApplication: CoreApplication() {
    override val isDebug: Boolean
        get() = TODO("Not yet implemented")
}