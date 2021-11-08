package ru.bestk1ng.calculator.helpers

import android.os.Parcelable
import java.lang.Exception
import kotlin.math.pow

enum class OperationName constructor(val symbol: String) {
    SUM("+"),
    SUBTRACT("-"),
    MULTIPLY("×"),
    DIVIDE("÷"),
    POWER("^"),
    ROOT("√");

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

        return operands[0] - operands[1]
    }
}

class MultiplyOperation : Operation() {
    override val name: OperationName
        get() = OperationName.MULTIPLY

    override val operandsCount: Int
        get() = 2

    override fun operate(operands: List<Double>): Double {
        if (operands.count() != operandsCount) {
            throw Exception("Wrong operand's count")
        }

        return operands[0] * operands[1]
    }
}

class DivideOperation : Operation() {
    override val name: OperationName
        get() = OperationName.DIVIDE

    override val operandsCount: Int
        get() = 2

    override fun operate(operands: List<Double>): Double {
        if (operands.count() != operandsCount) {
            throw Exception("Wrong operand's count")
        }

        return operands[0] / operands[1]
    }
}

class PowerOperation : Operation() {
    override val name: OperationName
        get() = OperationName.POWER

    override val operandsCount: Int
        get() = 2

    override fun operate(operands: List<Double>): Double {
        if (operands.count() != operandsCount) {
            throw Exception("Wrong operand's count")
        }

        return operands[0].pow(operands[1])
    }
}

class RootOperation : Operation() {
    override val name: OperationName
        get() = OperationName.ROOT

    override val operandsCount: Int
        get() = 2

    override fun operate(operands: List<Double>): Double {
        if (operands.count() != operandsCount) {
            throw Exception("Wrong operand's count")
        }

        return operands[0].pow(1/operands[1])
    }
}