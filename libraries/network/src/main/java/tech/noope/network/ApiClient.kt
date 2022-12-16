package tech.noope.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient<T>(
    private val baseUrl: String,
    private val service: Class<T>
) {

    private fun retrofitBuilder(): Retrofit {
        return Retrofit.Builder()
            .client(getClient())
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun call(): T {
        return retrofitBuilder().create(service)
    }

    private fun getClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(getInterceptor())
            .build()
    }

    private fun getInterceptor(): Interceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }
}