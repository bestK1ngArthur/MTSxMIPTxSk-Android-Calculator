package ru.bestk1ng.calculator

import android.content.Context
import android.os.Bundle
import android.os.VibrationEffect
import android.view.HapticFeedbackConstants
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding

import ru.bestk1ng.calculator.databinding.CalculatorFragmentBinding
import ru.bestk1ng.calculator.helpers.Calculator
import androidx.core.content.ContextCompat.getSystemService

import android.os.Vibrator
import android.widget.Toast


/**
 * Calculator Fragment.
 */
class CalculatorFragment : Fragment(R.layout.calculator_fragment) {
    private val viewBinding by viewBinding(CalculatorFragmentBinding::bind)
    private val viewModel: CalculatorViewModel by viewModels() {
        CalculatorViewModel.Factory(Calculator(), requireContext())
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
                    vibrate(viewModel.vibroValue)
                }
            }

            val operationsButtons = listOf(
                viewBinding.keyButtonDivision,
                viewBinding.keyButtonMultiplication,
                viewBinding.keyButtonSubtraction,
                viewBinding.keyButtonAddition,
                viewBinding.keyButtonPower,
                viewBinding.keyButtonRoot
            )

            operationsButtons.forEach {
                val name = it.text.toString()

                it.setOnClickListener {
                    viewModel.onOperation(name)
                    vibrate(viewModel.vibroValue)
                }
            }

            viewBinding.keyButtonEqual.setOnClickListener {
                viewModel.onResult()
                vibrate(viewModel.vibroValue)
            }

            viewBinding.keyButtonMode.setOnClickListener {
                viewModel.onReset()
                vibrate(viewModel.vibroValue)
            }

            viewBinding.keyButtonSign.setOnClickListener {
                viewModel.onSign()
                vibrate(viewModel.vibroValue)
            }

            viewBinding.keyButtonSettings.setOnClickListener {
                val action = CalculatorFragmentDirections.actionCalculatorFragmentToSettingsFragment()
                view.findNavController().navigate(action)
                vibrate(viewModel.vibroValue)
            }
        }
    }

    private fun vibrate(value: Int?) {
        if (value == null) { return }

        val vibrator = getSystemService(requireContext(), Vibrator::class.java)
        vibrator?.vibrate(VibrationEffect.createOneShot(100, value!!))

//        Toast.makeText(requireContext(), "Vibrate with ${value!!}", Toast.LENGTH_SHORT).show()
    }
}