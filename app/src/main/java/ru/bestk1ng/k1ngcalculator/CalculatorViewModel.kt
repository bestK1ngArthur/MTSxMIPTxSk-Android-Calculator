package ru.bestk1ng.k1ngcalculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Calculator ViewModel.
 */
class CalculatorViewModel(private val calculator: Calculator): ViewModel() {
    val expression: LiveData<String>
        get() = _expression

    val result: LiveData<String>
        get() = _result

    private val _expression = MutableLiveData<String>();
    private val _result = MutableLiveData<String>();

    private val operands = mutableListOf<Double>();

    fun onDigit(digit: Double) {
        _result.postValue(digit.toString())
    }

    fun onOperation(name: String) {}

    fun onResult() {
        _expression.postValue("Yeeeaah")
        _result.postValue("0 wow 0")
    }

    fun onClear() {}
}
