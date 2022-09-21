package lee.module.kotlin.core.data.network

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.net.HttpURLConnection

abstract class RefreshTokenAuthenticator : Authenticator {

    /**
     * @return throw exception if refresh fail
     */
    abstract fun refreshToken(): String
    abstract fun onRefreshTokenFail(ex: Throwable)

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            synchronized(this) {
                runCatching {
                    refreshToken().takeUnless { it.isBlank() } ?: throw IllegalStateException("Token is Empty")
                }.onSuccess {
                    return response.request.updateRequestHeaderAuthorization(it)
                }.onFailure {
                    onRefreshTokenFail(it)
                    return null
                }
            }
        }
        return null
    }
}
