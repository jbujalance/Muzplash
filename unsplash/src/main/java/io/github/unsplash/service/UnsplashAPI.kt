package io.github.unsplash.service

import io.github.unsplash.model.UnsplashPhoto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Unsplash API interface used by Retrofit to build the client.
 */
interface UnsplashAPI {

    /**
     * Retrieve random photos, given optional filters.
     * @param query Limit selection to photos matching a search term.
     * @param featured Limit selection to featured photos.
     * @param count The number of photos to return. (Default: 1; max: 30)
     * @return A call to the random photos endpoint
     */
    @GET("photos/random")
    fun getRandomPhotos(@Query("query") query: String, @Query("featured") featured: Boolean, @Query("count") count: Int): Call<Collection<UnsplashPhoto>>

    /**
     * Used to increment the number of downloads a photo has.
     * @param photoId The ID of the photo.
     * @return A call to the track endpoint
     */
    @GET("photos/{id}/download")
    fun trackDownload(@Path("id") photoId: String): Call<Any>
}