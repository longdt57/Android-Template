package lee.module.kotlin.pattern.data.network.error

import lee.module.kotlin.pattern.R
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ApiErrorUtils {
    const val MESSAGE = "message"
    const val CUSTOM_MESSAGE = "customMessage"
    const val INVALID_FIELD_NAMES = "invalidFieldNames"
    const val VIOLATION = "violation"

    fun getError(throwable: Throwable): ApiError {
        return when (throwable) {
            is SocketTimeoutException -> ApiError("Server not responding. Please try again later")
            is UnknownHostException, is IOException -> ApiError("Communication with server failed. Please check your internet connection.")
            is HttpException -> getErrorFromHttpException(throwable)
            else -> getDefaultApiError(throwable)
        }
    }

    fun getErrorMapFromHttpException(throwable: Throwable): Map<String, Any> {
        if (throwable !is HttpException) return mapOf()
        return GsonUtil.fromJson<Map<String, Any>>(throwable.response()?.errorBody()?.string()) ?: mapOf()
    }

    private fun getErrorFromHttpException(throwable: HttpException): ApiError {
        val errorBody: String? = throwable.response()?.errorBody()?.string()
        return GsonUtil.fromJson<ApiError>(errorBody) ?: getDefaultApiError(throwable)
    }

    private fun getDefaultApiError(throwable: Throwable) = ApiError(throwable.message)
}