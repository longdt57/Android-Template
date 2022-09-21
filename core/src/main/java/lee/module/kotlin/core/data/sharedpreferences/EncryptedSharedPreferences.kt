package lee.module.kotlin.core.data.sharedpreferences

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

@SuppressLint("ObsoleteSdkInt")
open class EncryptedSharedPreferences constructor(
    applicationContext: Context,
    prefName: String = applicationContext.packageName + "_encrypted_preferences",
) : BaseSharedPreferences(applicationContext, prefName) {

    final override val sharedPreferences: SharedPreferences

    init {
        val masterKeyBuilder = MasterKey.Builder(applicationContext)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val spec = KeyGenParameterSpec.Builder(
                MasterKey.DEFAULT_MASTER_KEY_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
                .build()
            masterKeyBuilder.setKeyGenParameterSpec(spec)
        }
        sharedPreferences = EncryptedSharedPreferences.create(
            applicationContext,
            prefName,
            masterKeyBuilder.build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}
