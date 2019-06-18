package io.github.muzplash.provider

import android.content.Context
import io.github.muzplash.model.MuzplashSettings
import io.github.muzplash.model.MuzplashSettingsImpl
import io.github.unsplash.model.UnsplashPhoto
import io.github.unsplash.service.UnsplashService
import io.github.unsplash.service.UnsplashServiceImpl
import java.util.function.Supplier

class UnsplashPhotoSupplier(private val settings: MuzplashSettings, private val unsplashService: UnsplashService): Supplier<Collection<UnsplashPhoto>> {

    constructor(context: Context, unsplashService: UnsplashService): this(MuzplashSettingsImpl(context), unsplashService)

    constructor(context: Context): this(context, UnsplashServiceImpl())

    override fun get(): Collection<UnsplashPhoto> {
        // TODO catch service exceptions
        return unsplashService.getRandomPhotos(settings.getSearchQuery(), settings.getLoadBatchSize())
    }
}