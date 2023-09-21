package com.example.cliwatch.personalGoal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_goal_details.view.goal_details
import kotlinx.android.synthetic.main.fragment_goal_details.view.no_notes
import kotlinx.android.synthetic.main.fragment_goal_details.view.progress
import kotlinx.android.synthetic.main.fragment_goal_details.view.title
import com.example.cliwatch.R
import com.example.cliwatch.personalGoal.data.Goal
import com.example.cliwatch.personalGoal.data.GoalDetails
import com.example.cliwatch.personalGoal.data.GoalViewModel

class GoalDetailsFragment : Fragment(), GoalDetailsNotifier {

    private lateinit var goalsViewModel: GoalViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        goalsViewModel =
            ViewModelProviders.of(this).get(GoalViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_goal_details, container, false)
        val goal = arguments?.getParcelable("goal") as Goal
        root.title.text = goal.title
        val progressText =
            goal.completedDays.toString() + " / " + goal.totalDays.toString()
        root.progress.text = progressText
        val recyclerView = root.goal_details
        goalsViewModel.goalDetails(goal.id).observe(this, Observer {
            toggleVisibility(root.no_notes, recyclerView, it.isEmpty())
            recyclerView.adapter =
                GoalDetailsAdapter(
                    activity,
                    it ?: mutableListOf(),
                    this
                )
        })
        recyclerView.layoutManager = LinearLayoutManager(activity)
        return root
    }

    private fun toggleVisibility(
        noNotes: View,
        recyclerView: RecyclerView,
        isEmpty: Boolean
    ) {
        noNotes.visibility = if(isEmpty) View.VISIBLE else View.GONE
        recyclerView.visibility = if(isEmpty) View.GONE else View.VISIBLE
    }

    override fun updateGoalDetails(goalDetails: GoalDetails) {
        val alert = AlertDialog.Builder(context!!)
        alert.setMessage(R.string.notes_heading)
        val input = EditText(context!!)
        input.setText(goalDetails.notes)
        alert.setView(input)
        alert.setPositiveButton(R.string.ok) { _, _ ->
            goalDetails.notes = input.text.toString()
            goalsViewModel.updateGoalDetails(goalDetails)
        }
        alert.show()
    }
}

interface GoalDetailsNotifier {
    fun updateGoalDetails(goalDetails: GoalDetails)
}