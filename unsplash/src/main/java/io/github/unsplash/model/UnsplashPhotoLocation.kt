package io.github.unsplash.model

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

data class UnsplashPhotoLocation(
        val title: String?,
        val city: String?,
        val country: String?,
        val position: UnsplashPhotoPosition?
) {
    fun getGMapsUriString(): String {
        var uriString = "geo:"
        uriString += position?.toString() ?: "0,0"
        if (title != null) uriString += "?q=" + URLEncoder.encode(title, StandardCharsets.UTF_8.toString())
        return uriString
    }
}