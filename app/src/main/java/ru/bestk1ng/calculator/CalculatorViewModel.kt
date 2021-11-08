package ru.bestk1ng.calculator

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import ru.bestk1ng.calculator.helpers.Calculator
import ru.bestk1ng.calculator.helpers.Equation
import ru.bestk1ng.calculator.helpers.OperationName
import ru.bestk1ng.calculator.helpers.Settings

/**
 * Calculator ViewModel.
 */
class CalculatorViewModel(private val calculator: Calculator, private val context: Context): ViewModel() {
    class Factory(private val calculator: Calculator, private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CalculatorViewModel(calculator, context) as T
        }
    }

    val expression: LiveData<String>
        get() = _expression

    val result: LiveData<String>
        get() = _result


    val vibroValue: Int?
        get() = if (settings.vibroIsOn) settings.vibroValue else null

    private val settings = Settings(context)

    private val _expression = MutableLiveData<String>()
    private val _result = MutableLiveData<String>()

    private val operands = mutableListOf<Double>()

    private var currentOperand: Double? = null
    private var currentOperationName: OperationName? = null

    private var isAfterResult: Boolean = false

    private val hasOperation: Boolean
        get() = currentOperationName != null

    fun onDigit(digit: Double) {
        if (isAfterResult) {
            isAfterResult = false
            currentOperand = null
            operands.clear()
        }

        currentOperand = (currentOperand ?: 0.0) * 10 + digit

        _result.postValue(formatValue(currentOperand!!))
    }

    fun onOperation(name: String) {
        if (isAfterResult) {
            isAfterResult = false
        }

        if (currentOperand == null) {
            //TODO: Handle error
            return
        }

        if (hasOperation) {
            onComplexOperation(name)
        } else {
            onSimpleOperation(name)
        }
    }

    fun onResult() {
        if ((currentOperand == null) || (currentOperationName == null)) {
            //TODO: Handle error
            return
        }

        operands.add(currentOperand!!)

        val operation = calculator.calculate(operands, currentOperationName!!)

        _expression.postValue(_expression.value + " ${ formatValue(currentOperand!!) }")
        _result.postValue(formatValue(operation.result))

        operands.clear()

        currentOperand = operation.result
        currentOperationName = null

        isAfterResult = true
    }

    fun onReset() {
        if (isAfterResult) {
            isAfterResult = false
        }

        _expression.postValue("")
        _result.postValue("")

        operands.clear()

        currentOperand = null
        currentOperationName = null

        calculator.reset()
    }

    fun onSign() {
        if (currentOperand == null) {
            return
        }

        currentOperand = -1.0 * currentOperand!!
        _result.postValue(formatValue(currentOperand!!))
    }

    fun onEquation(operands: String, result: String) {
        this.operands.clear()

        currentOperand = result.toDouble()
        currentOperationName = null

        _expression.postValue("$operands")
//        _expression.postValue("${ formatValue(operands[0]) } ${equation.operation} ${ formatValue(operands[1]) }")
        _result.postValue(formatValue(currentOperand!!))

        isAfterResult = true
    }

    private fun onSimpleOperation(name: String) {
        val operationName = OperationName.from(name)!!

        operands.add(currentOperand!!)

        _expression.postValue("${ formatValue(currentOperand!!) } $name")
        _result.postValue("")

        currentOperationName = operationName
        currentOperand = null
    }

    private fun onComplexOperation(name: String) {
        operands.add(currentOperand!!)

        val lastOperationName = currentOperationName!!
        val lastOperation = calculator.calculate(operands, lastOperationName)

        operands.clear()
        operands.add(lastOperation.result)

        currentOperand = null
        currentOperationName = OperationName.from(name)!!

        _expression.postValue("${ formatValue(lastOperation.result!!) } $name")
        _result.postValue("")
    }

    private fun formatValue(value: Double): String {
        val intValue = value.toInt()

        return if ((value - intValue) > 0) {
            String.format("%.${settings.accuracy}f", value)
        } else {
            intValue.toString()
        }
    }
}