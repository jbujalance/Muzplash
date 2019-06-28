package io.github.muzplash.model

/**
 * Holds the Muzplqsh configuration and settings.
 */
interface MuzplashSettings {

    // TODO download quality: regular, full, raw

    /**
     * @return The search query to send to the Unsplash endpoint.
     * For example `landscapes` if the user wants to retrieve photos of landscapes.
     */
    fun getSearchQuery(): String

    /**
     * @return `true` if the provided images should only be geolocated, `false` if both geolocated and non geolocated images can be provided
     */
    fun isGeolocatedFiltered(): Boolean

    /**
     * @return the number of images to load from Unsplash each time Muzei asks for new images.
     */
    fun getLoadBatchSize(): Int

    /**
     * @return number of images to load from Unsplash before filtering them on geolocation.
     * This batch size is based in a totally arbitrary estimation that half of the images in Unsplash are geolocated.
     */
    fun getLoadBatchSizeForGeolocationFiltering(): Int {
        return getLoadBatchSize() * 2
    }
}