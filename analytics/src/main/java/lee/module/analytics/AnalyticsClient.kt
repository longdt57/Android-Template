package lee.module.analytics

import android.os.Bundle

interface AnalyticsClient {
    @AnalyticsClientType
    val type: Int

    fun logEvent(event: AnalyticsEvent)
    fun logEvent(name: String, params: Bundle?)

    fun logScreenView(screenView: AnalyticsScreenView)
    fun logHandledThrowable(error: AnalyticsError) {}
    fun log(message: String)

    fun setUserProperty(property: AnalyticsUserProperty) {}
    fun setUserId(userId: String)
}