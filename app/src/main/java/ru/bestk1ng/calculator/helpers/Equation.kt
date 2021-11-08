package ru.bestk1ng.calculator.helpers

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Equation(
    val operands: List<Double>,
    val operation: String,
    val result: Double
) : Parcelable {
    fun expression(): String {
        var string = ""

        operands.forEachIndexed { index, operand ->
            string += "$operand "

            if (index != operands.size - 1) {
                string += "${operation} "
            }
        }

        string += "= $result"

        return string
    }
}
