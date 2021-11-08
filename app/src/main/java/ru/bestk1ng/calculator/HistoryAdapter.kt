package ru.bestk1ng.calculator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import ru.bestk1ng.calculator.helpers.Equation

class HistoryAdapter(
    private val equations: Array<Equation>,
    private val onItemClick: ((Equation) -> Unit)
    ) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val equationTextView: TextView = view.findViewById(R.id.equation_textView)

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(equations[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // create new view with UI of weather item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.equation_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.equationTextView.text = equations[position].expression()
    }

    override fun getItemCount(): Int {
        return equations.size
    }
}