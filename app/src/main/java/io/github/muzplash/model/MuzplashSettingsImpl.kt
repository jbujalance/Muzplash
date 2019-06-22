package io.github.muzplash.model

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class MuzplashSettingsImpl(private val preferences: SharedPreferences) : MuzplashSettings {

    constructor(context: Context): this(PreferenceManager.getDefaultSharedPreferences(context))

    companion object {
        private const val SETTINGS_KEY_QUERY = "query"
        private const val SETTINGS_DEFAULT_QUERY = "nature wallpaper"
        private const val SETTINGS_KEY_GEOLOCATED = "geolocated"
        private const val SETTINGS_KEY_BATCH_SIZE = "batch_size"
        private const val SETTINGS_DEFAULT_BATCH_SIZE = "3"
    }

    override fun getSearchQuery(): String {
        return preferences.getString(SETTINGS_KEY_QUERY, SETTINGS_DEFAULT_QUERY)!!  //Not nullable as fallbacks to default
    }

    override fun isGeolocatedFiltered(): Boolean {
        return preferences.getBoolean(SETTINGS_KEY_GEOLOCATED, false)
    }

    override fun getLoadBatchSize(): Int {
        // TODO EditTextPreference sucks. I need to create a custom NumberPickerPreference extending DialogPreference
        val string = preferences.getString(SETTINGS_KEY_BATCH_SIZE, SETTINGS_DEFAULT_BATCH_SIZE)!!  // Not null by definition
        return Integer.valueOf(string)
    }
}