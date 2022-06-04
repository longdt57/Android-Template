package lee.module.kotlin.pattern.data.network

import okhttp3.Interceptor
import okhttp3.Response

abstract class AccessTokenInterceptor : Interceptor {

    abstract fun getAccessToken(): String

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().updateRequestHeaderAuthorization(getAccessToken())
        return chain.proceed(request)
    }
}
