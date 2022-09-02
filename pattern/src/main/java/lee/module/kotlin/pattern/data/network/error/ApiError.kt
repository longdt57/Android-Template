package lee.module.kotlin.pattern.data.network.error

import com.google.gson.annotations.SerializedName

open class ApiError(
    @SerializedName("message")
    private val message: String?,
    @SerializedName("code")
    private val code: Int? = null
) {

    open fun getMessage(): String {
        return message.orEmpty()
    }
}