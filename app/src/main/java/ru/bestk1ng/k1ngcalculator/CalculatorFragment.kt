package ru.bestk1ng.k1ngcalculator

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding

import ru.bestk1ng.k1ngcalculator.databinding.CalculatorFragmentBinding

/**
 * Calculator Fragment.
 */
class CalculatorFragment : Fragment(R.layout.calculator_fragment) {

    private val viewBinding by viewBinding(CalculatorFragmentBinding::bind)
    private val viewModel: CalculatorViewModel by viewModels() {
        CalculatorViewModel.Factory(Calculator())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {

            viewModel.expression.observe(viewLifecycleOwner, Observer { expression ->
                viewBinding.expressionTextView.text = expression.toString()
            })

            viewModel.result.observe(viewLifecycleOwner, Observer { result ->
                viewBinding.resultTextView.text = result.toString()
            })

            val digitButtons = listOf(
                viewBinding.keyButtonValue0,
                viewBinding.keyButtonValue1,
                viewBinding.keyButtonValue2,
                viewBinding.keyButtonValue3,
                viewBinding.keyButtonValue4,
                viewBinding.keyButtonValue5,
                viewBinding.keyButtonValue6,
                viewBinding.keyButtonValue7,
                viewBinding.keyButtonValue8,
                viewBinding.keyButtonValue9
            )

            digitButtons.forEach {
                val digit = it.text
                    .toString()
                    .toDouble()

                it.setOnClickListener {
                    viewModel.onDigit(digit)
                }
            }

            val operationsButtons = listOf(
                viewBinding.keyButtonDivision,
                viewBinding.keyButtonMultiplication,
                viewBinding.keyButtonSubtraction,
                viewBinding.keyButtonAddition
            )

            operationsButtons.forEach {
                val name = it.text.toString()

                it.setOnClickListener {
                    viewModel.onOperation(name)
                }
            }

            viewBinding.keyButtonEqual.setOnClickListener {
                viewModel.onResult()
            }

            viewBinding.keyButtonMode.setOnClickListener {
                viewModel.onReset()
            }
        }
    }
}