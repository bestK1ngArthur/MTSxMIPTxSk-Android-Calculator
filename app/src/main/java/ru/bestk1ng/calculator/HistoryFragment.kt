package ru.bestk1ng.calculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding

import ru.bestk1ng.calculator.databinding.HistoryFragmentBinding
import ru.bestk1ng.calculator.helpers.Calculator
import ru.bestk1ng.calculator.helpers.Equation
import ru.bestk1ng.calculator.helpers.SubtractOperation
import ru.bestk1ng.calculator.helpers.SumOperation

class HistoryFragment : Fragment() {
    private val viewBinding by viewBinding(HistoryFragmentBinding::bind)
    private val viewModel: HistoryViewModel by viewModels() {
        HistoryViewModel.Factory(Calculator())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.history_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = viewBinding.recyclerView

        val adapter = HistoryAdapter(arrayOf(
            Equation(listOf(1.0, 2.0), SumOperation(), 3.0),
            Equation(listOf(3.0, 1.0), SubtractOperation(), 2.0))
        ) { equation ->
            println("CLICK ON ITEM $equation")
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        with(viewBinding) {
            keyButtonBack.setOnClickListener {
                view.findNavController().popBackStack()
            }
        }
    }
}