package lee.module.kotlin.core.data.network.error

import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import lee.module.kotlin.core.util.GsonUtil
import retrofit2.HttpException

object ApiErrorUtils {

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