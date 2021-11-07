package ru.bestk1ng.calculator.helpers

import java.lang.Exception

class Calculator {
    private val operations = setOf(
        SumOperation(),
        SubtractOperation(),
        MultiplyOperation(),
        DivideOperation(),
        PowerOperation(),
        RootOperation()
    )
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

    fun reset() {
        equations.clear()
    }

    private fun getOperation(name: OperationName): Operation? {
        return operations.firstOrNull { it.name == name }
    }
}