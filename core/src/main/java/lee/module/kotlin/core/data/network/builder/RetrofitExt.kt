package lee.module.kotlin.core.data.network.builder

import okhttp3.OkHttpClient

fun OkHttpClient.Builder.addHeaders(headers: Map<String, String>): OkHttpClient.Builder {
    addInterceptor { chain ->
        val original = chain.request()
        val builder = original.newBuilder()

        for ((key, value) in headers) {
            builder.addHeader(key, value)
        }

        builder.method(original.method, original.body)
        chain.proceed(builder.build())
    }
    return this
}
