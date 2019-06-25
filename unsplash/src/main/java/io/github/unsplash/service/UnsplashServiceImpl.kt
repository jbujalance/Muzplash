package io.github.unsplash.service

import io.github.unsplash.model.UnsplashPhoto

class UnsplashServiceImpl(private val api: UnsplashAPI, private val executor: CallExecutor): UnsplashService {

    constructor(accessKey: String): this(UnsplashAPIFactory.createUnsplashAPI(accessKey), CallExecutor())

    override fun getRandomPhotos(query: String, count: Int): Collection<UnsplashPhoto> {
        return executor.execute(api.getRandomPhotos(query, count))
    }

    override fun trackDownload(photoId: String) {
        executor.execute(api.trackDownload(photoId))
    }
}