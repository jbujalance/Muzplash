package io.github.muzplash.model

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import io.github.muzplash.BuildConfig

/**
 * Muzplash settings holder that delegates the configuration persistence on a SharedPreference instance.
 * @property preferences The instance of SharedPreferences that actually has access to the app preferences.
 */
class MuzplashSettingsImpl(private val preferences: SharedPreferences) : MuzplashSettings {

    /**
     * @property context The application context used to get the default shared preferences.
     */
    constructor(context: Context): this(PreferenceManager.getDefaultSharedPreferences(context))

    companion object {
        /** The key in the shared preferences that holds the search query value. */
        private const val SETTINGS_KEY_QUERY = "query"
        /** The default value for the search query in case there is not any registered value in the shared preferences. */
        private const val SETTINGS_DEFAULT_QUERY = "wallpaper"
        /** The key in the shared preferences that holds the geolocation filtering flag value. */
        private const val SETTINGS_KEY_GEOLOCATED = "geolocated"
        /** The key in the shared preferences that holds the featured filtering flag value. */
        private const val SETTINGS_KEY_FEATURED = "featured"
        /** The key in the shared preferences that holds the load batch size value. */
        private const val SETTINGS_KEY_BATCH_SIZE = "batch_size"
        /** The default value for the load batch size in case there is not any registered value in the shared preferences. */
        private const val SETTINGS_DEFAULT_BATCH_SIZE = 3
        /** The key in the shared preferences that holds the custom Unsplash API access key value. */
        private const val SETTINGS_KEY_ACCESS_KEY = "access_key"
    }

    override fun getSearchQuery(): String {
        return preferences.getString(SETTINGS_KEY_QUERY, SETTINGS_DEFAULT_QUERY)!!  //Not nullable as fallbacks to default
    }

    override fun isGeolocatedFiltered(): Boolean {
        return preferences.getBoolean(SETTINGS_KEY_GEOLOCATED, false)
    }

    override fun isFeaturedFiltered(): Boolean {
        return preferences.getBoolean(SETTINGS_KEY_FEATURED, true)
    }

    override fun getLoadBatchSize(): Int {
        return preferences.getInt(SETTINGS_KEY_BATCH_SIZE, SETTINGS_DEFAULT_BATCH_SIZE)
    }

    override fun getAccessKey(): String {
        return preferences.getString(SETTINGS_KEY_ACCESS_KEY, BuildConfig.UNSPLASH_ACCESS_KEY)!!  //Not nullable as fallbacks to default
    }
}