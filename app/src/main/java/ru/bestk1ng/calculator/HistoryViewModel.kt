package ru.bestk1ng.calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import ru.bestk1ng.calculator.helpers.Calculator

class HistoryViewModel(private val calculator: Calculator) : ViewModel() {
    class Factory(private val calculator: Calculator) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return HistoryViewModel(calculator) as T
        }
    }
}