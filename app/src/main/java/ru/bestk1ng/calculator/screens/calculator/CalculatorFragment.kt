package ru.bestk1ng.calculator.screens.calculator

import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding

import ru.bestk1ng.calculator.databinding.CalculatorFragmentBinding
import ru.bestk1ng.calculator.helpers.Calculator
import ru.bestk1ng.calculator.R

/**
 * Calculator Fragment.
 */
class CalculatorFragment : Fragment(R.layout.calculator_fragment) {
    private val viewBinding by viewBinding(CalculatorFragmentBinding::bind)
    private val viewModel: CalculatorViewModel by viewModels() {
        CalculatorViewModel.Factory(Calculator, requireContext())
    }

    private val args: CalculatorFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            viewModel.expression.observe(viewLifecycleOwner, Observer { expression ->
                expressionTextView.text = expression.toString()
            })

            viewModel.result.observe(viewLifecycleOwner, Observer { result ->
                resultTextView.text = result.toString()
            })

            val digitButtons = listOf(
                keyButtonValue0,
                keyButtonValue1,
                keyButtonValue2,
                keyButtonValue3,
                keyButtonValue4,
                keyButtonValue5,
                keyButtonValue6,
                keyButtonValue7,
                keyButtonValue8,
                keyButtonValue9
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
                keyButtonDivision,
                keyButtonMultiplication,
                keyButtonSubtraction,
                keyButtonAddition,
                keyButtonPower,
                keyButtonRoot
            )

            operationsButtons.forEach {
                val name = it.text.toString()

                it.setOnClickListener {
                    viewModel.onOperation(name)
                    vibrate(viewModel.vibroValue)
                }
            }

            keyButtonEqual.setOnClickListener {
                viewModel.onResult()
                vibrate(viewModel.vibroValue)
            }

            keyButtonMode.setOnClickListener {
                viewModel.onReset()
                vibrate(viewModel.vibroValue)
            }

            keyButtonSign.setOnClickListener {
                viewModel.onSign()
                vibrate(viewModel.vibroValue)
            }

            keyButtonSettings.setOnClickListener {
                val action = CalculatorFragmentDirections.actionCalculatorFragmentToSettingsFragment()
                view.findNavController().navigate(action)
                vibrate(viewModel.vibroValue)
            }

            keyButtonHistory.setOnClickListener {
                val action = CalculatorFragmentDirections.actionCalculatorFragmentToHistoryFragment()
                view.findNavController().navigate(action)
                vibrate(viewModel.vibroValue)
            }
        }

        if ((args.result != null) && (args.operands != null)) {
            viewModel.onEquation(args.operands!!, args.result!!)
        }
    }

    private fun vibrate(value: Int?) {
        if (value == null) { return }

        val vibrator = getSystemService(requireContext(), Vibrator::class.java)
        vibrator?.vibrate(VibrationEffect.createOneShot(100, value!!))

        // To debug vibration on simulator
        // Toast.makeText(requireContext(), "Vibrate with ${value!!}", Toast.LENGTH_SHORT).show()
    }
}