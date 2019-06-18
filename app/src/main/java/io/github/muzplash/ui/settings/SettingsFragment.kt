package io.github.muzplash.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import io.github.muzplash.R

class SettingsFragment: PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

}