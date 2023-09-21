package com.example.cliwatch.personalGoal.data

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_goal_details.view.edit_icon
import kotlinx.android.synthetic.main.view_goal_details.view.notes
import kotlinx.android.synthetic.main.view_goal_details.view.notes_date
import java.text.SimpleDateFormat
import java.util.*

class GoalDetailsViewHolder(
    view: View,
    listener: EditGoalListener
) : RecyclerView.ViewHolder(view) {

    private val notes = view.notes
    private val date = view.notes_date
    private val edit = view.edit_icon.setOnClickListener {
        listener.updateGoalDetails(adapterPosition)
    }

    fun setData(goalDetails: GoalDetails) {
        val formattedDate = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.ENGLISH).format(goalDetails.date)
        notes.text = goalDetails.notes.ifEmpty { "-" }
        date.text = formattedDate
    }
}