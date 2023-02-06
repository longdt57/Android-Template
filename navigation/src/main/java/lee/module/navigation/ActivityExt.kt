package lee.module.navigation

import android.app.Activity
import android.content.Intent
import lee.module.navigation.deeplink.DeeplinkIntents

fun Activity.openRootActivity() {
    val intent = DeeplinkIntents.getRootIntent().apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    startActivity(intent)
}
