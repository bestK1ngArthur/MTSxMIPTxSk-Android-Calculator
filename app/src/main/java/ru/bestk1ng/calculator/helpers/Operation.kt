package ru.bestk1ng.calculator.helpers

import java.lang.Exception

enum class OperationName constructor(val symbol: String) {
    SUM("+"),
    SUBTRACT("-"),
    MULTIPLY("×"),
    DIVIDE("÷");

    companion object {
        fun from(symbol: String): OperationName? = OperationName.values().firstOrNull { it.symbol == symbol }
    }
}

abstract class Operation {
    abstract val name: OperationName
    abstract val operandsCount: Int

    abstract fun operate(operands: List<Double>): Double
}

class SumOperation : Operation() {
    override val name: OperationName
        get() = OperationName.SUM

    override val operandsCount: Int
        get() = 2

    override fun operate(operands: List<Double>): Double {
        if (operands.count() != operandsCount) {
            throw Exception("Wrong operand's count")
        }

        return operands.sum()
    }
}

class SubtractOperation : Operation() {
    override val name: OperationName
        get() = OperationName.SUBTRACT

    override val operandsCount: Int
        get() = 2

    override fun operate(operands: List<Double>): Double {
        if (operands.count() != operandsCount) {
            throw Exception("Wrong operand's count")
        }

        return operands[1] - operands[2]
    }
}