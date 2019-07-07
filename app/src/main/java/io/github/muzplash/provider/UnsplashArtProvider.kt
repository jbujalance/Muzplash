package io.github.muzplash.provider

import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.apps.muzei.api.UserCommand
import com.google.android.apps.muzei.api.provider.Artwork
import com.google.android.apps.muzei.api.provider.MuzeiArtProvider
import io.github.muzplash.R
import io.github.muzplash.getGMapsUri
import io.github.muzplash.isGeolocated
import io.github.muzplash.model.MuzplashSettingsImpl
import io.github.unsplash.service.UnsplashService
import io.github.unsplash.service.UnsplashServiceImpl
import java.io.InputStream

/**
 * This is the main class of the Muzplash application.
 * This MuzeiArtProvider is the bridge between Muzei and Muzplash.
 * When Muzei wants to load new images, it asks this provider to load them.
 */
class UnsplashArtProvider : MuzeiArtProvider() {

    /**
     * The Unsplash service that will communicate with Unsplash to get the images.
     */
    private val unsplashService : UnsplashService by lazy {
        UnsplashServiceImpl(MuzplashSettingsImpl(context!!).getAccessKey())
    }

    companion object {
        /** The Id of the user command that allows to open the image in Google Maps. */
        private const val USER_COMMAND_ID_GMAPS = 1
    }

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

    override fun getCommands(artwork: Artwork): MutableList<UserCommand> {
        val commands = super.getCommands(artwork)
        if (artwork.isGeolocated()) commands.add(UserCommand(USER_COMMAND_ID_GMAPS, context?.getString(R.string.provider_command_gmaps)))
        return commands
    }

    override fun onCommand(artwork: Artwork, id: Int) {
        when(id) {
            USER_COMMAND_ID_GMAPS -> {
                val mapsIntent = Intent(Intent.ACTION_VIEW, artwork.getGMapsUri()).apply {
                    `package` = "com.google.android.apps.maps"
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                if (mapsIntent.resolveActivity(context?.packageManager!!) != null) {
                    context?.startActivity(mapsIntent)
                } else {
                    Toast.makeText(context, "Google Maps doesn't seem to be installed", Toast.LENGTH_SHORT).show()
                    Log.w(javaClass.simpleName, "The following intent failed to launch Google Maps: $mapsIntent")
                }
            }
        }
    }

    // TODO override useful methods like getCommands and getDescription
    // TODO override openArtworkInfo to start a photo details activity. The photo details can be stored in Artwork.metadata
}