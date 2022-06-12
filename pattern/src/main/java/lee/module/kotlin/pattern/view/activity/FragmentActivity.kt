package lee.module.kotlin.pattern.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

open class FragmentActivity : AppCompatActivity() {

    companion object {
        private const val FRAGMENT_TAG = "FRAGMENT_TAG"
        internal const val KEY_FRAGMENT = "KEY_FRAGMENT"

        fun getIntent(
            context: Context,
            clazz: Class<out Fragment>,
            args: Bundle? = null,
        ): Intent {
            val intent = Intent(context, FragmentActivity::class.java)
            intent.putExtra(KEY_FRAGMENT, clazz.name)
            if (args != null) intent.putExtras(args)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (supportFragmentManager.findFragmentByTag(FRAGMENT_TAG) == null) {
            addFragment()
        }
    }

    private fun addFragment() {
        val bundle = requireNotNull(intent.extras)
        val fragmentClazzName = requireNotNull(bundle.getString(KEY_FRAGMENT))

        try {
            val clazz = Class.forName(fragmentClazzName) as Class<out Fragment>
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(android.R.id.content, clazz, bundle, FRAGMENT_TAG)
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message.orEmpty(), Toast.LENGTH_SHORT).show()
            return
        }
    }
}
