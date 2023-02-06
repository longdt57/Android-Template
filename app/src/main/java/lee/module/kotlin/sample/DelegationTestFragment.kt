package lee.module.kotlin.sample

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.Fragment
import lee.module.kotlin.core.data.sharedpreferences.args
import lee.module.kotlin.core.data.sharedpreferences.argsNullable
import lee.module.kotlin.core.delegation.args
import lee.module.kotlin.core.delegation.argsNullable
import lee.module.kotlin.core.delegation.weakReference

private const val TAG = "DelegationTestFragment"

class DelegationTestFragment : Fragment() {

    private var title: String by args(defaultValue = "Lee is handsome")
    private var description: String? by argsNullable()

    private val sharedPreference: SharedPreferences by lazy {
        requireContext().getSharedPreferences(TAG, Context.MODE_PRIVATE)
    }

    private val userName: String by sharedPreference.args(defaultValue = "longdt57@gmail.com")
    private val avatar: String? by sharedPreference.argsNullable()

    private val age: Int? by weakReference(10)
}
