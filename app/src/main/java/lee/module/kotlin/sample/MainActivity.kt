package lee.module.kotlin.sample

import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import lee.module.kotlin.core.data.sharedpreferences.EncryptedSharedPreferences
import lee.module.kotlin.core.data.sharedpreferences.argsNullable

class MainActivity : AppCompatActivity() {

    private lateinit var encryptedPreference: CustomEncrypt

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        encryptedPreference = CustomEncrypt(this)

        findViewById<EditText>(R.id.testText).apply {
            setText(encryptedPreference.userName)

            doAfterTextChanged {
                encryptedPreference.userName = it.toString()
            }
        }

    }

    class CustomEncrypt(applicationContext: Context) : EncryptedSharedPreferences(applicationContext) {
        var userName: String? by sharedPreferences.argsNullable()
    }
}