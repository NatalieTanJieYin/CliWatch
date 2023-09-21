package com.example.cliwatch.personalGoal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_goal.view.count
import kotlinx.android.synthetic.main.fragment_goal.view.filled_exposed_dropdown
import kotlinx.android.synthetic.main.fragment_goal.view.menu_items
import com.example.cliwatch.R
import androidx.appcompat.app.AppCompatActivity
import com.example.cliwatch.personalGoal.data.GoalViewModel
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.cliwatch.personalGoal.data.Goal
import com.example.cliwatch.personalGoal.data.GoalDetails
import com.example.cliwatch.personalGoal.data.isCompleted


class GoalFragment : Fragment() {

    private lateinit var goalViewModel: GoalViewModel
    private var goal: Goal? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        goalViewModel =
            ViewModelProviders.of(this).get(GoalViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_goal, container, false)
        val textView: TextView = root.count
        onCounterIncrement(textView)
        setCounterValue(textView)

        goalViewModel.goals.observe(this, Observer {
            val title: String
            if (it.isEmpty()) {
                root.menu_items.visibility = View.GONE
                textView.background = resources.getDrawable(R.drawable.ic_circle_gradient)
                title = getString(R.string.start_goals)
                goalViewModel.text.value = getString(R.string.start_now)
            } else {
                goal = it?.find { selected -> selected.isSelected }
                onGoalSelected(root, it)
                setGoalParams(textView, goal?.isCompleted() == true)
                title = goal?.title ?: ""
            }
            (activity as AppCompatActivity).supportActionBar?.title = title
        })
        return root
    }

    private fun onGoalSelected(
        root: View,
        goals: List<Goal>
    ) {
        val adapter = ArrayAdapter(
            context!!,
            R.layout.dropdown_menu_popup_item,
            goals.map { c -> c.title }
        )
        val editTextFilledExposedDropdown = root.filled_exposed_dropdown
        editTextFilledExposedDropdown.setAdapter(adapter)
        editTextFilledExposedDropdown.setText(goal?.title, false)
        editTextFilledExposedDropdown.setOnItemClickListener { _, _, position, _ ->
            goal = goals[position]
            goalViewModel.unSelectAllGoals()
            goal?.isSelected = true
            goalViewModel.updateGoal(goal)
        }
    }

    private fun setGoalParams(textView: TextView, isDone: Boolean) {
        goalViewModel.text.value =
            if (isDone) getString(R.string.done) else goal?.completedDays.toString()
        if (isDone)
            textView.background = resources.getDrawable(R.drawable.ic_circle_gradient)
        else
            textView.setBackgroundResource(R.drawable.ic_count_background)
        textView.isEnabled = !isDone
    }

    private fun setCounterValue(textView: TextView) =
        goalViewModel.text.observe(this, Observer {
            textView.text = it.toString()
        })

    private fun onCounterIncrement(textView: TextView) = textView.setOnClickListener {
        goal?.let {
            if (it.hasNotes) {
                captureNotes(it.id)
            }
            it.completedDays = goal?.completedDays?.plus(1) ?: 0
            goalViewModel.updateGoal(goal)
            goalViewModel.text.value = goal?.completedDays.toString()
        } ?: replaceFragment(AddGoalsFragment())
    }

    private fun captureNotes(goalId: Long): GoalDetails {
        val goalDetails = GoalDetails()
        val alert = AlertDialog.Builder(context!!)
        alert.setMessage(R.string.notes_heading)
        val input = EditText(context!!)
        alert.setView(input)
        alert.setPositiveButton(R.string.ok) { _, _ ->
            goalDetails.notes = input.text.toString()
            goalViewModel.insertGoalDetails(goalDetails)
        }
        goalDetails.goalId = goalId
        alert.show()
        return goalDetails
    }

    private fun replaceFragment(fragment: Fragment) {
        fragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment, fragment)
            ?.addToBackStack(null)?.commit()
    }
}