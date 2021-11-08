package ru.bestk1ng.calculator.helpers

import java.lang.Exception

object Calculator {
    val equations: List<Equation>
        get() = _equations

    private val operations = setOf(
        SumOperation(),
        SubtractOperation(),
        MultiplyOperation(),
        DivideOperation(),
        PowerOperation(),
        RootOperation()
    )

    private val _equations = mutableListOf<Equation>()

    fun calculate(
        operands: List<Double>,
        operationName: OperationName
    ): Equation {
        val operation = getOperation(operationName) ?: throw Exception("Operation not found")
        val result = operation.operate(operands)
        val equation = Equation(operands, operation, result)

        _equations.add(equation)
        return equation
    }

    fun reset() {
        _equations.clear()
    }

    private fun getOperation(name: OperationName): Operation? {
        return operations.firstOrNull { it.name == name }
    }
}