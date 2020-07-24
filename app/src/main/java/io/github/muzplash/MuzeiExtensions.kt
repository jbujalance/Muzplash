package io.github.muzplash

import android.net.Uri
import androidx.core.net.toUri
import com.google.android.apps.muzei.api.provider.Artwork
import io.github.unsplash.model.UnsplashPhoto

/**
 * Important information. The property `metadata` of the returned Artwork is used to store the Google Maps URI string of the photo location, if any.
 * @return a Muzei Artwork object holding the details of this Unsplash photo.
 */
fun UnsplashPhoto.toArtwork(): Artwork {
    val photo = this
    return Artwork(
        token = photo.id,
        title = photo.getDescriptionSummary() ?: photo.altDescription ?: photo.getDefaultDescription(),
        byline = photo.location?.title ?: photo.getFormattedCreationDate(),
        attribution = photo.user.getAttribution(),
        persistentUri = photo.urls.full.toUri(),
        webUri = photo.links.getUtmHtml("Muzplash").toUri(),
        metadata = photo.location?.getGMapsUriString()
    )
}

/**
 * @return `true` if this Artwork is geolocated, `false` otherwise.
 */
fun Artwork.isGeolocated(): Boolean {
    return !metadata.isNullOrEmpty()
}

/**
 * @return The Google Maps URI of the artwork location, if the artwork is geolocated.
 * If the artwork is not geolocated, null is returned.
 */
fun Artwork.getGMapsUri(): Uri? {
    return if (isGeolocated()) Uri.parse(metadata) else null
}