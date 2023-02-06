package lee.module.kotlin.core

import android.app.Application

abstract class CoreApplication: Application() {

    abstract val isDebug: Boolean

}