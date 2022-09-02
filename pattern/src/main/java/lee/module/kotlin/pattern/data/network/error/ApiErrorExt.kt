package lee.module.kotlin.pattern.data.network.error

fun Throwable.getApiError(): ApiError {
    return ApiErrorUtils.getError(this)
}