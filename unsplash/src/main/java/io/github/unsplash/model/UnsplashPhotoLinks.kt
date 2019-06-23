package io.github.unsplash.model

data class UnsplashPhotoLinks(val html: String) {

    fun getUtmHtml(source: String): String {
        return "$html?utm_source=$source&utm_medium=referral"
    }
}