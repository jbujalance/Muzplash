package io.github.muzplash.model

interface MuzplashSettings {

    fun getSearchQuery(): String

    fun isGeolocatedFiltered(): Boolean

    fun getLoadBatchSize(): Int

    fun getLoadBatchSizeForGeolocationFiltering(): Int {
        return getLoadBatchSize() * 2
    }
}