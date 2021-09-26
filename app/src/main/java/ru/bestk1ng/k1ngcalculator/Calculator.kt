package ru.bestk1ng.k1ngcalculator

import java.lang.Exception

class Calculator {
    private val operations = setOf(SumOperation(), SubtractOperation())
    private val equations = mutableListOf<Equation>()

    fun calculate(
        operands: List<Double>,
        operationName: OperationName
    ): Equation {
        val operation = getOperation(operationName) ?: throw Exception("Operation not found")
        val result = operation.operate(operands)
        val equation = Equation(operands, operation, result)

        equations.add(equation)
        return equation
    }

    fun clear() {
        equations.clear()
    }

    private fun getOperation(name: OperationName): Operation? {
        return operations.firstOrNull { it.name == name }
    }
}