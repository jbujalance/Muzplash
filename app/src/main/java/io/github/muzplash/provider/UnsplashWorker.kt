package io.github.muzplash.provider

import android.content.Context
import androidx.work.*
import com.google.android.apps.muzei.api.provider.ProviderContract
import io.github.muzplash.model.MuzplashSettingsImpl
import io.github.muzplash.toArtwork

/**
 * Worker responsible for downloading a batch of images from the Unsplash backend and notifying the UnsplashArtProvider that new images are available.
 * @property context the application context
 */
class UnsplashWorker(private val context: Context, parameters: WorkerParameters) : Worker(context, parameters) {
    // TODO manage dependency injection in worker

    override fun doWork(): Result {
        val photos = UnsplashPhotoSupplier(MuzplashSettingsImpl(context)).get()
        val providerClient = ProviderContract.getProviderClient(applicationContext, UnsplashArtProvider::class.java)
        providerClient.addArtwork(photos.map { it.toArtwork() })
        return Result.success()
    }

    companion object {
        /**
         * Enqueues an UnsplashWorker request to the WorkManager. The request is constrained by a not-roaming network connection.
         */
        internal fun enqueueWork() {
            val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.NOT_ROAMING).build()
            val request = OneTimeWorkRequestBuilder<UnsplashWorker>().setConstraints(constraints).build()
            WorkManager.getInstance().enqueue(request)
        }
    }
}