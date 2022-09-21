package lee.module.kotlin.core.data.network.error

fun Throwable.getApiError(): ApiError {
    return ApiErrorUtils.getError(this)
}