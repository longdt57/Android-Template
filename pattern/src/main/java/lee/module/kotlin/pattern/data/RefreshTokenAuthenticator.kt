package lee.module.kotlin.pattern.data

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.net.HttpURLConnection

class RefreshTokenAuthenticator constructor(
    private val refreshTokenJob: () -> String, // Return accessToken
    private val onRefreshFail: ((Exception) -> Unit)? = null
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
            synchronized(this) {
                return exeRefreshToken().takeUnless { it.isNullOrBlank() }?.let {
                    updateRequestHeaderAuthorization(response.request(), it)
                }
            }
        }
        return null
    }

    private fun updateRequestHeaderAuthorization(request: Request, accessToken: String): Request {
        return request.updateRequestHeaderAuthorization(accessToken)
    }

    /**
     * Return accessToken if success, null or empty if error
     */
    private fun exeRefreshToken(): String? {
        return try {
            refreshTokenJob.invoke()
        } catch (e: Exception) {
            onRefreshFail?.invoke(e)
            null
        }
    }
}
