package io.github.unsplash.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

data class UnsplashPhoto(
        val id: String,
        @JsonProperty("created_at")
        val creationDate: ZonedDateTime?,
        val description: String?,
        @JsonProperty("alt_description")
        val altDescription: String?,
        val location: UnsplashPhotoLocation?,
        val urls: UnsplashPhotoUrls,
        val links: UnsplashPhotoLinks,
        val user: UnsplashUser
) {

    companion object {
        private const val DESCRIPTION_MAX_LENGHT = 80
        private const val DEFAULT_DESCRIPTION = "Image from Unsplash"
    }

    fun isGeolocated(): Boolean {
        return location != null
    }

    /**
     * @return A trunkated description of the image
     */
    fun getDescriptionSummary(): String? {
        if (description == null) return null
        return if (description.length <= DESCRIPTION_MAX_LENGHT) description else description.substring(0, DESCRIPTION_MAX_LENGHT).plus("...")
    }

    /**
     * @return A default description of the image, to use in case the image description of the alternative description are not available
     */
    fun getDefaultDescription(): String {
        return DEFAULT_DESCRIPTION
    }

    /**
     * @return The formatted creation date. The date is formatted depending in the JVMs default locale
     */
    fun getFormattedCreationDate(): String? {
        return creationDate?.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))
    }
}