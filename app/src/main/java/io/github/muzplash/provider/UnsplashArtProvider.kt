package io.github.muzplash.provider

import android.util.Log
import com.google.android.apps.muzei.api.provider.Artwork
import com.google.android.apps.muzei.api.provider.MuzeiArtProvider
import io.github.unsplash.service.UnsplashService
import io.github.unsplash.service.UnsplashServiceImpl
import java.io.InputStream

class UnsplashArtProvider(val unsplashService: UnsplashService) : MuzeiArtProvider() {

    constructor(): this(UnsplashServiceImpl())

    override fun onLoadRequested(initial: Boolean) {
        UnsplashWorker.enqueueWork()
    }

    override fun openFile(artwork: Artwork): InputStream {
        return super.openFile(artwork).also {
            artwork.token?.run {
                try {
                    unsplashService.trackDownload(this)
                } catch (e: Exception) {
                    // TODO instead of catching Exception, catch a custom Exception thrown by the CallExecutor
                    Log.w("UnsplashArtProvide", "An error occurred while reporting the download to Unsplash", e)
                }
            }
        }
    }

    // TODO override useful methods like getCommands and getDescription
    // TODO override openArtworkInfo to start a photo details activity. The photo details can be stored in Artwork.metadata
}