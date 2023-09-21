package com.example.cliwatch.personalGoal.data

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_goals.view.goal_item
import kotlinx.android.synthetic.main.view_goals.view.delete_icon
import kotlinx.android.synthetic.main.view_goals.view.edit_icon
import kotlinx.android.synthetic.main.view_goals.view.progress
import kotlinx.android.synthetic.main.view_goals.view.title

class ViewGoalViewHolder(view: View, listener: ViewGoalListener) : RecyclerView.ViewHolder(view) {
    private val title = view.title
    private val progress = view.progress
    private val delete = view.delete_icon.setOnClickListener {
        listener.deleteGoal(adapterPosition)
    }
    private val edit  = view.edit_icon.setOnClickListener {
        listener.editGoal(adapterPosition)
    }

    private val goalItem  = view.goal_item.setOnClickListener {
        listener.onItemSelected(adapterPosition)
    }
    fun setData(goal: Goal) {
        title.text = goal.id.toString()
        title.text = goal.title
        val progressText = goal.completedDays.toString() + " / " + goal.totalDays.toString()
        progress.text = progressText
    }
}