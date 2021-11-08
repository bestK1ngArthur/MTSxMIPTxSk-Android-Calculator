package ru.bestk1ng.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import ru.bestk1ng.calculator.helpers.Calculator
import ru.bestk1ng.calculator.helpers.Equation

class HistoryViewModel(private val calculator: Calculator) : ViewModel() {
    class Factory(private val calculator: Calculator) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return HistoryViewModel(calculator) as T
        }
    }

    val equations: LiveData<List<Equation>>
        get() = _equations

    private val _equations = MutableLiveData<List<Equation>>()

    fun onAppear() {
        _equations.postValue(calculator.equations)
    }
}