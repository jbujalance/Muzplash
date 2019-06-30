package io.github.muzplash.provider

import android.content.Context
import io.github.muzplash.BuildConfig
import io.github.muzplash.model.MuzplashSettings
import io.github.muzplash.model.MuzplashSettingsImpl
import io.github.unsplash.model.UnsplashPhoto
import io.github.unsplash.service.UnsplashService
import io.github.unsplash.service.UnsplashServiceImpl
import java.util.function.Supplier
import java.util.stream.Collectors
import java.util.stream.Stream

/**
 * A Supplier of Unsplash photos, backed by an Unsplash service
 * @property settings The Muzei settings holder that will impose how the photos are loaded from Unsplash.
 * @property unsplashService The Unsplash service that actually gets in touch with the Unsplash endpoints to laod the imaages
 */
class UnsplashPhotoSupplier(private val settings: MuzplashSettings, private val unsplashService: UnsplashService): Supplier<Collection<UnsplashPhoto>> {

    constructor(context: Context, unsplashService: UnsplashService): this(MuzplashSettingsImpl(context), unsplashService)

    constructor(context: Context, unsplashAccessKey: String): this(context, UnsplashServiceImpl(unsplashAccessKey))

    constructor(context: Context): this(context, BuildConfig.UNSPLASH_ACCESS_KEY)

    override fun get(): Collection<UnsplashPhoto> {
        if (settings.isGeolocatedFiltered()) {
            return getStream { unsplashService.getRandomPhotos(settings.getSearchQuery(), settings.isFeaturedFiltered(), settings.getLoadBatchSizeForGeolocationFiltering()) }
                    .filter{ it.isGeolocated() }
                    .limit(settings.getLoadBatchSize().toLong())
                    .collect(Collectors.toList())
        }
        // TODO catch service exceptions ? Maybe not, as this code runs in a Worker, so kind of useless
        return unsplashService.getRandomPhotos(settings.getSearchQuery(), settings.isFeaturedFiltered(), settings.getLoadBatchSize())
    }

    /**
     * Builds a Stream backed by the given supplier.
     * @param supplier An Unsplash photo supplier that will be accessed from the built Stream.
     * @return A Stream of Unsplash photos backed by the given supplier.
     */
    private fun getStream(supplier: () -> Collection<UnsplashPhoto>): Stream<UnsplashPhoto> {
        return Stream.generate(supplier).flatMap { collection -> collection.stream() }
    }

}