package ru.bestk1ng.calculator.helpers

data class Equation(
    val operands: List<Double>,
    val operation: Operation,
    val result: Double
) {
    fun expression(): String {
        var string = ""

        operands.forEachIndexed { index, operand ->
            string += "$operand "

            if (index != operands.size - 1) {
                string += "${operation.name.symbol} "
            }
        }

        string += "= $result"

        return string
    }
}
