package lee.module.kotlin.pattern.data

import okhttp3.Request

internal const val HEADER_AUTHORIZATION = "Authorization"

internal fun Request.getHeaderAuthorization(): String? = header(HEADER_AUTHORIZATION)

internal fun Request.updateRequestHeaderAuthorization(accessToken: String): Request {
    val authorizationHeader = "Bearer $accessToken"
    return newBuilder()
        .removeHeader(HEADER_AUTHORIZATION)
        .addHeader(HEADER_AUTHORIZATION, authorizationHeader)
        .build()
}

internal fun Request.hasNewAccessToken(localAccessToken: String): Boolean {
    return getHeaderAuthorization().orEmpty().contains(localAccessToken).not()
}
