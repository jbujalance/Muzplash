package io.github.muzplash.model

interface MuzplashSettings {

    // TODO download quality: regular, full, raw

    fun getSearchQuery(): String

    fun isGeolocatedFiltered(): Boolean

    fun getLoadBatchSize(): Int

    fun getLoadBatchSizeForGeolocationFiltering(): Int {
        return getLoadBatchSize() * 2
    }
}