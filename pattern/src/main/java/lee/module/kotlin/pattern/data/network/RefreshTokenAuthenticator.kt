package lee.module.kotlin.pattern.data.network

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.net.HttpURLConnection

abstract class RefreshTokenAuthenticator : Authenticator {

    /**
     * @return accessToken, or null if fail
     */
    abstract fun refreshToken(): String?
    abstract fun onRefreshTokenFail(ex: Exception)

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
            synchronized(this) {
                return executeRefreshToken().takeUnless { it.isNullOrBlank() }?.let {
                    response.request().updateRequestHeaderAuthorization(it)
                }
            }
        }
        return null
    }

    /**
     * Return accessToken if success, null or empty if error
     */
    private fun executeRefreshToken(): String? {
        return try {
            refreshToken()
        } catch (e: Exception) {
            onRefreshTokenFail(e)
            null
        }
    }
}
