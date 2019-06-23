package io.github.unsplash.model

data class UnsplashUser(
        val id: String,
        val username: String,
        val name: String?,
        val bio: String?,
        val location: String?,
        val links: UnsplashUserLinks
) {

    fun getAttribution(): String {
        return "Photo by $name on Unsplash"
    }
}