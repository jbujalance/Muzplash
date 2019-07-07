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
     * @return `true` if the provided photos should all be featured, `false` otherwise.
     */
    fun isFeaturedFiltered() : Boolean

    /**
     * @return the configured number of images to load from Unsplash each time Muzei asks for new images.
     */
    fun getLoadBatchSize(): Int

    /**
     * @return The actual number of photos to retrieve from the Unsplash API.
     * This number depends on whether the photos need to be manually filtered or not.
     * When a manual filtering is needed, we retrieve more images from the Unsplash API as some of them will be lost.
     * The objective is that, even after dropping the invalid photos, we are able to retain `loadBatchSize` photos after filtering with just one request.
     */
    fun getRequestPhotoCount(): Int {
        return if (isManualFilteringNeeded()) getLoadBatchSizeForManualFiltering() else getLoadBatchSize()
    }

    /**
     * @return The custom Unsplash API access key if present, or the default key if none has been provided by the user.
     */
    fun getAccessKey(): String

    /**
     * A manual filter is a filter that is not supported by the Unsplash API.
     * For example, the featured filter is supported by the Unsplash API, but the geolocation or likes filters are not.
     * @return number of images to load from Unsplash before filtering them manually.
     * This batch size is based in a totally arbitrary estimation.
     */
    private fun getLoadBatchSizeForManualFiltering(): Int {
        return getLoadBatchSize() * 3
    }

    /**
     * A manual filter is a filter that is not supported by the Unsplash API.
     * For example, the featured or collection filter is supported by the Unsplash API, but the geolocation or likes filters are not.
     * @return `true` if the photos will need to be manually filtered to satisfy the settings, `false` otherwise.
     */
    private fun isManualFilteringNeeded(): Boolean {
        return isGeolocatedFiltered()
    }
}