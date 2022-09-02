package lee.module.kotlin.pattern.view

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

// ---------- FRAGMENT ---------- //

fun Fragment.replaceFragment(
    target: Fragment,
    addToBackStack: Boolean = true,
    containerId: Int = android.R.id.content,
    tag: String? = null
) {
    moveTo(containerId, target, addToBackStack, false, tag)
}

fun Fragment.addFragment(
    target: Fragment,
    addToBackStack: Boolean = true,
    containerId: Int = android.R.id.content,
    tag: String? = null
) {
    moveTo(containerId, target, addToBackStack, true, tag)
}

private fun Fragment.moveTo(
    containerId: Int,
    target: Fragment,
    addToBackStack: Boolean,
    isAdd: Boolean,
    tag: String?
) {
    parentFragmentManager.commit {
        setCustomAnimations(
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right,
        )
        if (isAdd) add(containerId, target, tag) else replace(containerId, target, tag)
        if (addToBackStack) addToBackStack(null)
    }
}

fun Fragment.getForwardIntent(): Intent {
    val intent = Intent().apply {
        requireActivity().intent.extras?.let {
            putExtras(it)
        }
    }
    return intent
}
