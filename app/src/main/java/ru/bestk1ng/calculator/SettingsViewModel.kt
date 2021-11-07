package ru.bestk1ng.calculator

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import ru.bestk1ng.calculator.helpers.Settings

/**
 * Settings ViewModel.
 */
class SettingsViewModel(val context: Context) : ViewModel() {
    private val settings = Settings(context)

    class Factory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SettingsViewModel(context) as T
        }
    }

    val accuracy: LiveData<Int>
        get() = _accuracy

    val vibroIsOn: LiveData<Boolean>
        get() = _vibroIsOn

    val vibroValue: LiveData<Int>
        get() = _vibroValue

    private val _accuracy = MutableLiveData<Int>()
    private val _vibroIsOn = MutableLiveData<Boolean>()
    private val _vibroValue = MutableLiveData<Int>()

    fun onAppear() {
        _accuracy.postValue(settings.accuracy)
        _vibroIsOn.postValue(settings.vibroIsOn)
        _vibroValue.postValue(settings.vibroValue)
    }

    fun setAccuracy(value: Int) {
        settings.setAccuracy(value)
    }

    fun setVibro(isOn: Boolean) {
        settings.setVibro(isOn)
    }

    fun setVibroValue(value: Int) {
        settings.setVibroValue(value)
    }
}