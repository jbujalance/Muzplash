package io.github.unsplash.service

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.unsplash.exception.UnsplashAPIException
import io.github.unsplash.model.UnsplashError
import retrofit2.Call

class CallExecutor(private val mapper: ObjectMapper) {

    constructor(): this(UnsplashAPIFactory.getObjectMapper())

    fun <R> execute(call: Call<R>): R {
        // TODO catch IOException and wrap it in a UnsplashException
        val response = call.execute()
        if (response.isSuccessful) {
            return response.body()!!    // Body won't be null as the response is successful
        } else {
            val unsplashError = mapper.readValue(response.errorBody()?.string(), UnsplashError::class.java)
            throw UnsplashAPIException("The Unsplash server returned a failure response", response.code(), unsplashError)
        }
    }
}