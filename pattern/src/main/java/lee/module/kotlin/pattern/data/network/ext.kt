package lee.module.kotlin.pattern.data.network

import okhttp3.Request

internal const val HEADER_AUTHORIZATION = "Authorization"

internal fun Request.updateRequestHeaderAuthorization(accessToken: String): Request {
    val authorizationHeader = "Bearer $accessToken"
    return newBuilder()
        .removeHeader(HEADER_AUTHORIZATION)
        .addHeader(HEADER_AUTHORIZATION, authorizationHeader)
        .build()
}
