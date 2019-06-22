package io.github.muzplash.ui.settings

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.NumberPicker
import androidx.preference.DialogPreference
import androidx.preference.PreferenceDialogFragmentCompat
import io.github.muzplash.R

class NumberPickerPreference(context: Context, attrSet: AttributeSet): DialogPreference(context, attrSet) {

    private var maxValue: Int = DEFAULT_MAX_VALUE
    private var minValue: Int = DEFAULT_MIN_VALUE
    private var value: Int = DEFAULT_VALUE
        set(newValue) {
            field = newValue
            if (shouldPersist()) persistInt(field)
        }

    companion object {
        private const val DEFAULT_MAX_VALUE: Int = 10
        private const val DEFAULT_MIN_VALUE: Int = 1
        private const val DEFAULT_VALUE: Int = 2
    }

    init {
        val typedArray = context.obtainStyledAttributes(attrSet, R.styleable.NumberPickerPreference)
        maxValue = typedArray.getInt(R.styleable.NumberPickerPreference_maxValue, DEFAULT_MAX_VALUE)
        minValue = typedArray.getInt(R.styleable.NumberPickerPreference_minValue, DEFAULT_MIN_VALUE)
        typedArray.recycle()
    }

    override fun onSetInitialValue(defaultValue: Any?) {
        value = getPersistedInt(defaultValue as? Int ?: DEFAULT_VALUE)
    }

    class NumberPickerPreferenceFragment private constructor(private val preference: NumberPickerPreference): PreferenceDialogFragmentCompat() {

        private lateinit var picker: NumberPicker

        companion object {
            fun newInstance(preference: NumberPickerPreference): NumberPickerPreferenceFragment {
                val fragment = NumberPickerPreferenceFragment(preference)
                val b = Bundle(1)
                b.putString(ARG_KEY, preference.key)
                fragment.arguments = b
                return fragment
            }
        }

        override fun onCreateDialogView(context: Context?): View {
            picker = NumberPicker(context).apply {
                minValue = preference.minValue
                maxValue = preference.maxValue
                value = preference.getPersistedInt(preference.value)
            }
            return picker
        }

        override fun onDialogClosed(positiveResult: Boolean) {
            if (positiveResult) {
                picker.clearFocus()
                preference.value = picker.value
            }
        }

    }

}