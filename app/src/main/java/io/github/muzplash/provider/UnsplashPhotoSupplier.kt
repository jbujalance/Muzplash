package io.github.muzplash.provider

import io.github.muzplash.model.MuzplashSettings
import io.github.muzplash.provider.filtering.FilterChain
import io.github.muzplash.provider.filtering.GeolocationFilter
import io.github.unsplash.model.UnsplashPhoto
import io.github.unsplash.service.UnsplashService
import io.github.unsplash.service.UnsplashServiceImpl
import java.util.function.Supplier
import java.util.stream.Collectors

/**
 * A Supplier of Unsplash photos, backed by an Unsplash service
 * @property settings The Muzei settings holder that will impose how the photos are loaded from Unsplash.
 * @property unsplashService The Unsplash service that actually gets in touch with the Unsplash endpoints to laod the imaages
 */
class UnsplashPhotoSupplier(private val settings: MuzplashSettings, private val unsplashService: UnsplashService): Supplier<Collection<UnsplashPhoto>> {

    constructor(settings: MuzplashSettings): this(settings, UnsplashServiceImpl(settings.getAccessKey()))

    private val filterChain: FilterChain<UnsplashPhoto> by lazy {
        // We only have a manual filter right now, but we could have plenty of them defined here
        FilterChain(listOf(GeolocationFilter(settings)))
    }

    override fun get(): Collection<UnsplashPhoto> {
        val baseStream = unsplashService.getRandomPhotosStream(settings.getSearchQuery(), settings.isFeaturedFiltered(), settings.getRequestPhotoCount())
        return filterChain.filter(baseStream)
                .limit(settings.getLoadBatchSize().toLong())
                .collect(Collectors.toList())
    }

}