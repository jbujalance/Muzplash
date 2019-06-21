package io.github.muzplash

import androidx.core.net.toUri
import com.google.android.apps.muzei.api.provider.Artwork
import io.github.unsplash.model.UnsplashPhoto

fun UnsplashPhoto.toArtwork(): Artwork {
    val photo = this
    return Artwork().apply {
        token = photo.id
        title = photo.getDescriptionSummary() ?: photo.alt_description
        byline = photo.location?.title
        attribution = photo.user.name
        persistentUri = photo.urls.full.toUri()
        webUri = photo.links.html.toUri()
    }
}