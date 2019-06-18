package io.github.unsplash.service

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

object UnsplashAPIFactory {

    fun createUnsplashAPI(): UnsplashAPI {
        return createRetrofitBuilder().create(UnsplashAPI::class.java)
    }

    fun getObjectMapper(): ObjectMapper {
        return jacksonObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    private fun createRetrofitBuilder(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(UNSPLASH_BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create(getObjectMapper()))
                .client(createHttpClient())
                .build()
    }

    private fun createHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .callTimeout(20, TimeUnit.SECONDS)
                .addNetworkInterceptor(createHeaderInterceptor())
                .build()
    }

    private fun createHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept-Version", "v1")
                    .addHeader("Authorization", "Client-ID $UNSPLASH_ACCESS_KEY")
                    .build()
            chain.proceed(request)
        }
    }
}