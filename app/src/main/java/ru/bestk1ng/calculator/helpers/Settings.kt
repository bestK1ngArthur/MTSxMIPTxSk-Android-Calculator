package ru.bestk1ng.calculator.helpers

import android.content.Context
import android.content.SharedPreferences

class Settings(val context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences("calc", Context.MODE_PRIVATE)

    private val accuracyKey = "ACCURACY"
    private val vibroIsOnKey = "VIBRO_IS_ON"
    private val vibroValueKey = "VIBRO_VALUE"

    val accuracy: Int
        get() = preferences.getInt(accuracyKey, 2)

    val vibroIsOn: Boolean
        get() = preferences.getBoolean(vibroIsOnKey, false)

    val vibroValue: Int
        get() = preferences.getInt(vibroValueKey, 10)

    fun setAccuracy(value: Int) {
        val editor = preferences.edit()
        editor.putInt(accuracyKey, value)
        editor.commit()
    }

    fun setVibro(isOn: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean(vibroIsOnKey, isOn)
        editor.commit()
    }

    fun setVibroValue(value: Int) {
        val editor = preferences.edit()
        editor.putInt(vibroValueKey, value)
        editor.commit()
    }
}