package io.github.unsplash.model

data class UnsplashPhoto(
        val id: String,
        val description: String?,
        val location: UnsplashPhotoLocation?,
        val urls: UnsplashPhotoUrls,
        val links: UnsplashPhotoLinks,
        val user: UnsplashUser
)