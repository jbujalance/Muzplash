package io.github.muzplash.ui.settings

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import io.github.muzplash.R

class SettingsActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.settings_container, SettingsFragment()).commit()
        }
    }
}