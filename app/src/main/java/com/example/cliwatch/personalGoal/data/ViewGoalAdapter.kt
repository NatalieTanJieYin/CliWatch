package com.example.cliwatch.personalGoal.data

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cliwatch.R
import com.example.cliwatch.personalGoal.ViewGoalNotifier

class ViewGoalAdapter(
    private val context: Context?,
    private val goals: MutableList<Goal>,
    private val viewGoalNotifier: ViewGoalNotifier
) :
    RecyclerView.Adapter<ViewGoalViewHolder>(), ViewGoalListener {

    override fun deleteGoal(adapterPosition: Int) {
        viewGoalNotifier.deleteGoal(goals[adapterPosition], adapterPosition)
    }

    override fun editGoal(adapterPosition: Int) {
        viewGoalNotifier.editGoal(goals[adapterPosition])
        notifyDataSetChanged()
    }

    override fun onItemSelected(adapterPosition: Int) {
        viewGoalNotifier.onItemSelected(goals[adapterPosition])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewGoalViewHolder {
        return ViewGoalViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.view_goals,
                parent,
                false
            ), this
        )
    }

    override fun onBindViewHolder(holder: ViewGoalViewHolder, position: Int) {
        holder.setData(goals[position])
    }

    override fun getItemCount(): Int {
        return goals.size
    }

    fun deleteSuccess(adapterPosition: Int) {
        val isSelectedGoal = goals[adapterPosition].isSelected
        goals.removeAt(adapterPosition)
        if(isSelectedGoal && goals.size > 0) {
            goals[0].isSelected = true
            viewGoalNotifier.updateGoal(goals[0])
        }
        notifyDataSetChanged()
    }
}

interface ViewGoalListener {
    fun deleteGoal(adapterPosition: Int)

    fun editGoal(adapterPosition: Int)

    fun onItemSelected(adapterPosition: Int)
}