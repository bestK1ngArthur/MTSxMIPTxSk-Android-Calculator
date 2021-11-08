package ru.bestk1ng.calculator

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import ru.bestk1ng.calculator.helpers.Calculator
import ru.bestk1ng.calculator.helpers.Equation
import ru.bestk1ng.calculator.helpers.Settings

data class EquationItem(val equation: Equation, val text: String)

class HistoryViewModel(
    private val calculator: Calculator,
    private val context: Context
    ) : ViewModel() {
    class Factory(private val calculator: Calculator, private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return HistoryViewModel(calculator, context) as T
        }
    }

    val equations: LiveData<List<EquationItem>>
        get() = _equations

    private val _equations = MutableLiveData<List<EquationItem>>()

    private val settings = Settings(context)

    fun onAppear() {
        val items = calculator.equations.map {
            EquationItem(it, getText(it))
        }

        _equations.postValue(items)
    }

    private fun getText(equation: Equation): String {
        var string = ""

        equation.operands.forEachIndexed { index, operand ->
            string += "${ formatValue(operand) } "

            if (index != equation.operands.size - 1) {
                string += "${equation.operation} "
            }
        }

        string += "= ${ formatValue(equation.result) }"

        return string
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