package lee.module.kotlin.core

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.Fragment
import lee.module.kotlin.core.data.sharedpreferences.args
import lee.module.kotlin.core.data.sharedpreferences.argsNullable
import lee.module.kotlin.core.delegation.weakReference

class DelegationTestFragment : Fragment() {

    private var title: String by args(defaultValue = "Lee is handsome")
    private var description: String? by argsNullable()

    private val sharedPreference: SharedPreferences by lazy {
        requireContext().getSharedPreferences("DelegationTestFragment", Context.MODE_PRIVATE)
    }

    private val userName: String by sharedPreference.args(defaultValue = "longdt57@gmail.com")
    private val avatar: String? by sharedPreference.argsNullable()

    private val age: Int? by weakReference(10)
}