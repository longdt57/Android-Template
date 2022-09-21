package lee.module.kotlin.core.data.network

import okhttp3.Interceptor
import okhttp3.Response

abstract class AuthTokenInterceptor : Interceptor {

    abstract fun getAuthToken(): String

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().updateRequestHeaderAuthorization(getAuthToken())
        return chain.proceed(request)
    }
}
