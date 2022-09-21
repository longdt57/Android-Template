@file:JvmName("AnalyticsClientType")

package lee.module.analytics

import androidx.annotation.IntDef
import lee.module.analytics.ClientType.ALL
import lee.module.analytics.ClientType.APPSFLYER
import lee.module.analytics.ClientType.FACEBOOK
import lee.module.analytics.ClientType.FIREBASE
import lee.module.analytics.ClientType.INSIDER

@IntDef(FIREBASE, FACEBOOK, INSIDER, APPSFLYER, ALL)
@Retention(AnnotationRetention.SOURCE)
annotation class AnalyticsClientType

object ClientType {
    const val FIREBASE = 0b000001
    const val FACEBOOK = 0b000010
    const val INSIDER = 0b000100
    const val APPSFLYER = 0b001000
    const val ALL = FIREBASE or FACEBOOK or INSIDER or APPSFLYER
}
