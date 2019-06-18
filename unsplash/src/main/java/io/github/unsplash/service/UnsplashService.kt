package io.github.unsplash.service

import io.github.unsplash.model.UnsplashPhoto

interface UnsplashService {

    fun getRandomPhotos(query: String, count: Int): Collection<UnsplashPhoto>

    fun trackDownload(photoId: String): Unit

}