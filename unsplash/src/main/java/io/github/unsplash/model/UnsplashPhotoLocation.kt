package io.github.unsplash.model

data class UnsplashPhotoLocation(
        val city: String?,
        val country: String?
) {
    fun formatted(): String {
        val items = ArrayList<String>()
        city?.let { items.add(it) }
        country?.let { items.add(it) }
        return items.joinToString()
    }
}