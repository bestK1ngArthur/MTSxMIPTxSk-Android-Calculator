package ru.bestk1ng.k1ngcalculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import ru.bestk1ng.k1ngcalculator.databinding.CalculatorFragmentBinding

/**
 * Calculator Fragment.
 */
class CalculatorFragment : Fragment() {

    private lateinit var binding: CalculatorFragmentBinding
    private lateinit var viewModel: CalculatorViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = CalculatorFragmentBinding.inflate(inflater, container, false)
        viewModel = CalculatorViewModel(Calculator())

        viewModel.expression.observe(viewLifecycleOwner, Observer { expression ->
            binding.expressionTextView.text = expression.toString()
        })

        viewModel.result.observe(viewLifecycleOwner, Observer { result ->
            binding.resultTextView.text = result.toString()
        })

        val digitButtons = listOf(
            binding.keyButtonValue0,
            binding.keyButtonValue1,
            binding.keyButtonValue2,
            binding.keyButtonValue3,
            binding.keyButtonValue4,
            binding.keyButtonValue5,
            binding.keyButtonValue6,
            binding.keyButtonValue7,
            binding.keyButtonValue8,
            binding.keyButtonValue9
        )

        digitButtons.forEach {
            val digit = it.text
                .toString()
                .toDouble()

            it.setOnClickListener {
                viewModel.onDigit(digit)
            }
        }

        binding.keyButtonEqual.setOnClickListener {
            viewModel.onResult()
        }

        return binding.root
    }
}