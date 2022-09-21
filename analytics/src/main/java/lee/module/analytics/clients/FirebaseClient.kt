package lee.module.analytics.clients

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.ktx.Firebase
import lee.module.analytics.AnalyticsClient
import lee.module.analytics.AnalyticsError
import lee.module.analytics.AnalyticsEvent
import lee.module.analytics.AnalyticsScreenView
import lee.module.analytics.AnalyticsUserProperty
import lee.module.analytics.ClientType
import timber.log.Timber
import java.math.BigDecimal

class FirebaseClient(context: Context) : AnalyticsClient {

    override val type: Int get() = ClientType.FIREBASE

    private val firebaseAnalytics: FirebaseAnalytics get() = Firebase.analytics
    private val firebaseCrashlytics: FirebaseCrashlytics get() = FirebaseCrashlytics.getInstance()

    override fun logEvent(event: AnalyticsEvent) {
        val name = event.name.replace('.', '_')
        val bundle = event.params.toBundle()
//        firebaseCrashlytics.log("event $name")
        firebaseAnalytics.logEvent(name, bundle)
        Timber.d("event $name ${bundle.toString()}")
    }

    override fun logEvent(name: String, params: Bundle?) {
        val refactorName = name.replace('.', '_')
//        firebaseCrashlytics.log("event $name")
        firebaseAnalytics.logEvent(refactorName, params)
        Timber.d("event $refactorName ${params.toString()}")
    }

    override fun logScreenView(screenView: AnalyticsScreenView) {
        val name = screenView.name.replace('.', '_')

        firebaseAnalytics.logEvent(name, null)
//        firebaseCrashlytics.log("view $name")
        Timber.d("view $name")
    }

    override fun setUserProperty(property: AnalyticsUserProperty) {
        val value: String? = property.value.takeUnless { it.isNullOrBlank() }
        firebaseAnalytics.setUserProperty(property.name, value)
        firebaseCrashlytics.setCustomKey(property.name, value.orEmpty())
    }

    override fun logHandledThrowable(error: AnalyticsError) {
        firebaseCrashlytics.recordException(error.throwable)
        Timber.w(error.throwable, error.name)
    }

    override fun log(message: String) {
//        firebaseCrashlytics.log(message)
        Timber.d(message)
    }

    override fun setUserId(userId: String) {
        firebaseAnalytics.setUserId(userId)
        firebaseCrashlytics.setUserId(userId)

        firebaseAnalytics.setUserProperty(FIREBASE_USER_ID_PROPERTY, userId)
    }

    private fun Map<String, Any?>.toBundle(): Bundle {
        val bundle = Bundle()
        for ((key, value) in this) {
            when (value) {
                is Int -> bundle.putInt(key, value)
                is Float -> bundle.putFloat(key, value)
                is Double -> bundle.putDouble(key, value)
                is Boolean -> bundle.putBoolean(key, value)
                is String -> bundle.putString(key, value)
                is BigDecimal -> bundle.putString(key, value.toPlainString())
                else -> bundle.putString(key, value?.toString())
            }
        }
        return bundle
    }

    companion object {
        private const val FIREBASE_USER_ID_PROPERTY = "user_id"
    }
}