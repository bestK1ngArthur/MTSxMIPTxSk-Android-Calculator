package ru.bestk1ng.calculator.helpers

data class Equation(
    val operands: List<Double>,
    val operation: Operation,
    val result: Double
) {
    fun expression(): String {
        return operands.fold("", { acc, d -> acc + "${operation.name.symbol} $d"  })
    }
}
