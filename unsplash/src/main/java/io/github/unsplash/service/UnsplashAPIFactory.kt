package io.github.unsplash.service

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.threetenbp.ThreeTenModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

object UnsplashAPIFactory {

    private const val UNSPLASH_BASE_URL = "https://api.unsplash.com/"

    fun createUnsplashAPI(accessKey: String): UnsplashAPI {
        return createRetrofitBuilder(accessKey).create(UnsplashAPI::class.java)
    }

    fun getObjectMapper(): ObjectMapper {
        return jacksonObjectMapper()
                .registerModule(ThreeTenModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    private fun createRetrofitBuilder(accessKey: String): Retrofit {
        return Retrofit.Builder()
                .baseUrl(UNSPLASH_BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create(getObjectMapper()))
                .client(createHttpClient(accessKey))
                .build()
    }

    private fun createHttpClient(accessKey: String): OkHttpClient {
        return OkHttpClient.Builder()
                .callTimeout(20, TimeUnit.SECONDS)
                .addNetworkInterceptor(createHeaderInterceptor(accessKey))
                .build()
    }

    private fun createHeaderInterceptor(accessKey: String): Interceptor {
        return Interceptor { chain ->
            val request = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept-Version", "v1")
                    .addHeader("Authorization", "Client-ID $accessKey")
                    .build()
            chain.proceed(request)
        }
    }
}