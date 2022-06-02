package lee.module.kotlin.pattern.data

import okhttp3.Interceptor
import okhttp3.Response

class AccessTokenInterceptor(
    private val accessTokenProvider: () -> String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().updateRequestHeaderAuthorization(accessTokenProvider.invoke())
        return chain.proceed(request)
    }
}
