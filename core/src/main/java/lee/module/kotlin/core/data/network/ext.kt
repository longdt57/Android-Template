package lee.module.kotlin.core.data.network

import okhttp3.Request

internal const val HEADER_AUTHORIZATION = "Authorization"

internal fun Request.updateRequestHeaderAuthorization(token: String): Request {
    return newBuilder()
        .removeHeader(HEADER_AUTHORIZATION)
        .addHeader(HEADER_AUTHORIZATION, token)
        .build()
}
