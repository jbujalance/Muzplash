package io.github.unsplash.model

data class UnsplashPhoto(
        val id: String,
        val description: String?,
        val alt_description: String?,
        val location: UnsplashPhotoLocation?,
        val urls: UnsplashPhotoUrls,
        val links: UnsplashPhotoLinks,
        val user: UnsplashUser
) {

    companion object {
        const val DESCRIPTION_MAX_LENGHT = 80
    }

    fun isGeolocated(): Boolean {
        return location != null
    }

    fun getDescriptionSummary(): String? {
        if (description == null) return null
        return if (description.length <= DESCRIPTION_MAX_LENGHT) description else description.substring(0, DESCRIPTION_MAX_LENGHT).plus("...")
    }
}