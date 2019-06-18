package io.github.unsplash.service

import io.github.unsplash.model.UnsplashPhoto

class UnsplashServiceImpl(private val api: UnsplashAPI, private val executor: CallExecutor): UnsplashService {

    constructor(): this(UnsplashAPIFactory.createUnsplashAPI(), CallExecutor())

    override fun getRandomPhotos(query: String, count: Int): Collection<UnsplashPhoto> {
        return executor.execute(api.getRandomPhotos(query, count))
    }

    override fun trackDownload(photoId: String) {
        executor.execute(api.trackDownload(photoId))
    }
}