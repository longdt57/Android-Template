package lee.module.kotlin.pattern.view.model

import android.app.Activity
import android.content.Context
import android.widget.Toast

sealed class MessageUiState(
    private val message: String? = null,
    private val messageResId: Int? = null
) : UiState {

    private fun Int?.isValidResId(): Boolean = this != null && this != 0

    protected fun getMessage(context: Context): String {
        return if (messageResId.isValidResId()) {
            context.getString(messageResId!!)
        } else {
            message.orEmpty()
        }
    }

    abstract fun showMessage(activity: Activity): Boolean

    class ToastUiState(message: String? = null, messageResId: Int? = null) :
        MessageUiState(message = message, messageResId = messageResId) {

        override fun showMessage(activity: Activity): Boolean {
            Toast.makeText(activity, getMessage(activity), Toast.LENGTH_SHORT).show()
            return true
        }
    }
}
