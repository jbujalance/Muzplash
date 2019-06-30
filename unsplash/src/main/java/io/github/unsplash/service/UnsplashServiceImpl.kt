package io.github.unsplash.service

import io.github.unsplash.model.UnsplashPhoto

/**
 * Implementation of the Unsplash service.
 * @property api The API that will build the calls to the Unsplash API
 * @property executor It will actually execute the calls provided by the API
 */
class UnsplashServiceImpl(private val api: UnsplashAPI, private val executor: CallExecutor): UnsplashService {

    constructor(accessKey: String): this(UnsplashAPIFactory.createUnsplashAPI(accessKey), CallExecutor())

    override fun getRandomPhotos(query: String, featured: Boolean, count: Int): Collection<UnsplashPhoto> {
        return executor.execute(api.getRandomPhotos(query, featured, count))
    }

    override fun trackDownload(photoId: String) {
        executor.execute(api.trackDownload(photoId))
    }
}