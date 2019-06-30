package io.github.unsplash.service

import io.github.unsplash.model.UnsplashPhoto
import java.util.stream.Stream

/**
 * The interface between Muzplash and Unsplash. It exposes the operations that cen be asked to the Unsplash API.
 */
interface UnsplashService {

    /**
     * Get random photos matching te given parameters.
     * @param query Limit selection to photos matching a search term.
     * @param featured Limit selection to featured photos.
     * @param count The number of photos to return.
     * @return A collection of random photos matching the given parameters
     */
    fun getRandomPhotos(query: String, featured: Boolean, count: Int): Collection<UnsplashPhoto>

    /**
     * Used to increment the number of downloads a photo has.
     * @param photoId The ID of the photo.
     */
    fun trackDownload(photoId: String)

    /**
     * @param query Limit selection to photos matching a search term.
     * @param featured Limit selection to featured photos.
     * @param count The number of photos to return.
     * @return An infinite stream of random Unsplash photos matching the given parameters
     */
    fun getRandomPhotosStream(query: String, featured: Boolean, count: Int): Stream<UnsplashPhoto> {
        return Stream.generate { getRandomPhotos(query, featured, count) }.flatMap { collection -> collection.stream() }
    }

}