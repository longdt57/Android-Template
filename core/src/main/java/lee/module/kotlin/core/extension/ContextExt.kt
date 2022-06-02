package lee.module.kotlin.core.extension

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.util.TypedValue

private const val TAG = "ContextExt"

fun Context.openSettings() {
    try {
        startActivity(Intent(Settings.ACTION_SETTINGS))
    } catch (e: ActivityNotFoundException) {
        Log.d(TAG, e.message.orEmpty())
    }
}

fun Context.shareText(content: String) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, content)
        type = "text/plain"
    }
    try {
        startActivity(sendIntent)
    } catch (e: ActivityNotFoundException) {
        Log.d(TAG, e.message.orEmpty())
    }
}

fun Context.callTo(phone: String) {
    val callIntent = Intent().apply {
        action = Intent.ACTION_DIAL
        data = Uri.parse("tel:$phone")
    }
    try {
        startActivity(callIntent)
    } catch (e: ActivityNotFoundException) {
        Log.d(TAG, e.message.orEmpty())
    }
}

fun Context.sendEmail(email: String) {
    val emailIntent = Intent().apply {
        action = Intent.ACTION_SENDTO
        data = Uri.parse("mailto:$email")
    }
    try {
        startActivity(emailIntent)
    } catch (e: ActivityNotFoundException) {
        Log.d(TAG, e.message.orEmpty())
    }
}

fun Context.openPlayStore(pkg: String = packageName) {
    try {
        startActivity(
            Intent().apply {
                action = Intent.ACTION_VIEW
                data = Uri.parse("market://details?id=$pkg")
            }
        )
    } catch (e: ActivityNotFoundException) {
        Log.d(TAG, e.message.orEmpty())
    }
}

fun Context.gotoGMap(address: String) {
    val gMapAppPackageName = "com.google.android.apps.maps"
    val mapIntent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("https://www.google.com/maps/dir/?api=1&destination=$address")
    )

    if (isAppEnabled(gMapAppPackageName, packageManager)) {
        mapIntent.`package` = gMapAppPackageName
        startActivity(mapIntent)
    } else {
        startActivity(mapIntent)
    }
}

fun Context.dpToPx(value: Float): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        value,
        resources.displayMetrics
    ).toInt()
}

private fun Context.isAppEnabled(packageName: String, packageManager: PackageManager): Boolean {
    return try {
        val appInfo = packageManager.getApplicationInfo(packageName, 0)
        appInfo.enabled
    } catch (e: PackageManager.NameNotFoundException) {
        Log.d(TAG, e.message.orEmpty())
        false
    }
}
