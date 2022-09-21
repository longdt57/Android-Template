package lee.module.kotlin.core.data.network.builder

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import lee.module.kotlin.core.BuildConfig

open class BaseServiceBuilder {

    protected val headers: MutableMap<String, String> = mutableMapOf()
    protected val interceptors: MutableList<Interceptor> = ArrayList()
    protected val clientBuilder: OkHttpClient.Builder by lazy { OkHttpClient.Builder() }
    protected val converterFactories: MutableList<Converter.Factory> = mutableListOf()

    fun <T> create(clazz: Class<T>, endpoint: String): T {
        return createRetrofitBuilder(endpoint).build().create(clazz)
    }

    open fun createRetrofitBuilder(endpoint: String): Retrofit.Builder {
        return build(endpoint)
    }

    fun addHeader(key: String, value: String): BaseServiceBuilder {
        headers[key] = value
        return this
    }

    fun addInterceptor(interceptor: Interceptor): BaseServiceBuilder {
        interceptors.add(interceptor)
        return this
    }

    fun addConverterFactory(converterFactory: Converter.Factory) {
        converterFactories.add(converterFactory)
    }

    fun build(endpoint: String): Retrofit.Builder {
        clientBuilder.addHeaders(headers)
        interceptors.forEach {
            clientBuilder.addInterceptor(it)
        }

        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(
                HttpLoggingInterceptor().apply {
                    this.level = HttpLoggingInterceptor.Level.BODY
                }
            )
        }

        return Retrofit.Builder()
            .baseUrl(endpoint)
            .client(clientBuilder.build())
            .apply {
                converterFactories.forEach {
                    addConverterFactory(it)
                }
            }
    }
}
