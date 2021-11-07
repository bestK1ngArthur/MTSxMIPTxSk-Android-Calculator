package ru.bestk1ng.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Calculator ViewModel.
 */
class CalculatorViewModel(private val calculator: Calculator): ViewModel() {
    class Factory(private val calculator: Calculator) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CalculatorViewModel(calculator) as T
        }
    }

    val expression: LiveData<String>
        get() = _expression

    val result: LiveData<String>
        get() = _result

    private val _expression = MutableLiveData<String>()
    private val _result = MutableLiveData<String>()

    private val operands = mutableListOf<Double>()

    private var currentOperand = 0.0
    private var currentOperationName: OperationName? = null

    fun onDigit(digit: Double) {
        currentOperand = currentOperand * 10 + digit

        _result.postValue(formatValue(currentOperand))
    }

    fun onOperation(name: String) {
        val operationName = OperationName.from(name) ?: return

        operands.add(currentOperand)

        _expression.postValue("${ formatValue(currentOperand) } $name")
        _result.postValue("")

        currentOperationName = operationName
        currentOperand = 0.0
    }

    fun onResult() {
        val operationName = currentOperationName ?: return

        operands.add(currentOperand)

        val operation = calculator.calculate(operands, operationName)

        _expression.postValue(_expression.value + " ${ formatValue(currentOperand) }")
        _result.postValue(formatValue(operation.result))

        operands.clear()
        operands.add(operation.result)

        currentOperand = 0.0
        currentOperationName = null
    }

    fun onReset() {
        _expression.postValue("")
        _result.postValue("")

        operands.clear()

        currentOperand = 0.0
        currentOperationName = null

        calculator.reset()
    }

    private fun formatValue(value: Double): String {
        val intValue = value.toInt()

        return if ((value - intValue) > 0) {
            value.toString()
        } else {
            intValue.toString()
        }
    }
}