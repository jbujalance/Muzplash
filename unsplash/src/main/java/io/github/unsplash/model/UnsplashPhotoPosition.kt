package io.github.unsplash.model

data class UnsplashPhotoPosition(val latitude: Double, val longitude: Double) {

    override fun toString(): String {
        return "$latitude,$longitude"
    }

}