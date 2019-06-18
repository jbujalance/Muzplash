package io.github.unsplash.service

import io.github.unsplash.model.UnsplashPhoto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashAPI {

    // TODO handle HTTP errors and exceptions

    @GET("photos/random")
    fun getRandomPhotos(@Query("query") query: String, @Query("count") count: Int): Call<Collection<UnsplashPhoto>>

    @GET("photos/{id}/download")
    fun trackDownload(@Path("id") photoId: String): Call<Any>
}