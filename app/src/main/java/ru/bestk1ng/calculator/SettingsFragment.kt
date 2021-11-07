package ru.bestk1ng.calculator

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding

import ru.bestk1ng.calculator.databinding.SettingsFragmentBinding

/**
 * Settings Fragment.
 */
class SettingsFragment : Fragment() {
    private val viewBinding by viewBinding(SettingsFragmentBinding::bind)
    private val viewModel: SettingsViewModel by viewModels() {
        SettingsViewModel.Factory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewBinding) {
            viewBinding.keyButtonBack.setOnClickListener {
                view.findNavController().popBackStack()
            }

            viewModel.accuracy.observe(viewLifecycleOwner, Observer { accuracy ->
                viewBinding.accuracyNumber.setText(accuracy.toString())
            })

            viewModel.vibroIsOn.observe(viewLifecycleOwner, Observer { isOn ->
                viewBinding.vibroSwitch.isChecked = isOn
            })

            viewModel.vibroValue.observe(viewLifecycleOwner, Observer { value ->
                viewBinding.vibroBar.progress = value.toInt()
            })

            viewBinding.accuracyNumber.addTextChangedListener {
                viewModel.setAccuracy(accuracyNumber.text.toString().toIntOrNull() ?: 0)
            }

            viewBinding.vibroSwitch.setOnCheckedChangeListener { _, isChecked ->
                viewModel.setVibro(isChecked)
            }

            viewBinding.vibroBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, currentValue: Int, p2: Boolean) {
                    viewModel.setVibroValue(currentValue)
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {}
                override fun onStopTrackingTouch(p0: SeekBar?) {}
            })
        }

        viewModel.onAppear()
    }
}