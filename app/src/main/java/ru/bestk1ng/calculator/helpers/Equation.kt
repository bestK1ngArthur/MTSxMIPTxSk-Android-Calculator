package ru.bestk1ng.calculator.helpers

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Equation(
    val operands: List<Double>,
    val operation: String,
    val result: Double
) : Parcelable {}
