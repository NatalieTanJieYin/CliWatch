package com.example.cliwatch.personalGoal.data

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cliwatch.R
import com.example.cliwatch.personalGoal.GoalDetailsNotifier

class GoalDetailsAdapter(
    private val context: Context?,
    private val goalDetails: List<GoalDetails>,
    private val goalDetailsNotifier: GoalDetailsNotifier
) : RecyclerView.Adapter<GoalDetailsViewHolder>(), EditGoalListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalDetailsViewHolder {
        return GoalDetailsViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.view_goal_details,
                parent,
                false
            ),
            this
        )
    }

    override fun onBindViewHolder(holder: GoalDetailsViewHolder, position: Int) {
        holder.setData(goalDetails[position])
    }

    override fun getItemCount(): Int {
        return goalDetails.size
    }

    override fun updateGoalDetails(position: Int) {
        goalDetailsNotifier.updateGoalDetails(goalDetails[position])
    }
}

interface EditGoalListener {
    fun updateGoalDetails(position: Int)
}