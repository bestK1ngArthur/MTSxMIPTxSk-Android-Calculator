package ru.bestk1ng.k1ngcalculator

abstract class Operation {
    abstract val displaySymbol: String
    abstract val operandsCount: Int

    abstract fun operate(operands: List<Double>): Double
}

class SumOperation(
    override val displaySymbol: String,
    override val operandsCount: Int
) : Operation() {
    override fun operate(operands: List<Double>): Double {
        return operands.sum()
    }
}

class SubtractOperation(
    override val displaySymbol: String,
    override val operandsCount: Int
) : Operation() {
    override fun operate(operands: List<Double>): Double {
        return operands.sum()
    }
}