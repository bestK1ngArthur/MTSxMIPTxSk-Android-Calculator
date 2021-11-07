package ru.bestk1ng.calculator

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Test
import org.junit.Assert.*
import org.junit.Rule
import org.junit.rules.TestRule
import ru.bestk1ng.calculator.helpers.Calculator

/**
 * CalculatorViewModel Unit Tests
 */
class CalculatorViewModelTest {
    private val viewModel = CalculatorViewModel(Calculator())

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Test
    fun addingDigits_isCorrect() {
        viewModel.onDigit(1.0)
        viewModel.onDigit(2.0)
        viewModel.onDigit(3.0)

        assertEquals("123", viewModel.result.value)
    }

    @Test
    fun simpleSum_isCorrect() {
        viewModel.onDigit(2.0)
        viewModel.onDigit(1.0)
        viewModel.onOperation("+")
        viewModel.onDigit(1.0)
        viewModel.onResult()

        assertEquals("21 + 1", viewModel.expression.value)
        assertEquals("22", viewModel.result.value)
    }

    @Test
    fun complexSum_isCorrect() {
        viewModel.onDigit(1.0)
        viewModel.onOperation("+")

        assertEquals("1 +", viewModel.expression.value)
        assertEquals("", viewModel.result.value)

        viewModel.onDigit(2.0)
        viewModel.onOperation("+")

        assertEquals("3 +", viewModel.expression.value)
        assertEquals("", viewModel.result.value)

        viewModel.onDigit(3.0)
        viewModel.onResult()

        assertEquals("3 + 3", viewModel.expression.value)
        assertEquals("6", viewModel.result.value)
    }

    @Test
    fun operationSeries_isCorrect() {
        viewModel.onDigit(1.0)
        viewModel.onOperation("+")
        viewModel.onDigit(2.0)
        viewModel.onResult()

        assertEquals("1 + 2", viewModel.expression.value)
        assertEquals("3", viewModel.result.value)

        viewModel.onDigit(3.0)
        viewModel.onOperation("+")
        viewModel.onDigit(4.0)
        viewModel.onResult()

        assertEquals("3 + 4", viewModel.expression.value)
        assertEquals("7", viewModel.result.value)
    }

    @Test
    fun operationAfterResult_isCorrect() {
        viewModel.onDigit(1.0)
        viewModel.onOperation("+")
        viewModel.onDigit(2.0)
        viewModel.onResult()

        assertEquals("1 + 2", viewModel.expression.value)
        assertEquals("3", viewModel.result.value)

        viewModel.onOperation("+")
        viewModel.onDigit(4.0)
        viewModel.onResult()

        assertEquals("3 + 4", viewModel.expression.value)
        assertEquals("7", viewModel.result.value)
    }
}