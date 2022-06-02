package lee.module.kotlin.pattern.data

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.net.HttpURLConnection

class RefreshTokenAuthenticator constructor(
    private val accessTokenProvider: () -> String,
    private val refreshTokenJob: () -> Unit,
    private val onRefreshFail: ((Exception) -> Unit)? = null
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
            synchronized(this) {
                return when {
                    isAccessTokenUpdated(response.request()) -> updateRequestHeaderAuthorization(
                        response.request()
                    )
                    refreshToken() -> updateRequestHeaderAuthorization(response.request())
                    else -> response.request()
                }
            }
        }
        return null
    }

    private fun updateRequestHeaderAuthorization(request: Request): Request =
        request.updateRequestHeaderAuthorization(accessTokenProvider.invoke())

    private fun isAccessTokenUpdated(request: Request): Boolean {
        return request.hasNewAccessToken(accessTokenProvider.invoke())
    }

    private fun refreshToken(): Boolean {
        return try {
            refreshTokenJob.invoke()
            true
        } catch (e: Exception) {
            onRefreshFail?.invoke(e)
            false
        }
    }
}
