package com.example.cliwatch.personalGoal

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_edit_goal.view.completed
import kotlinx.android.synthetic.main.fragment_edit_goal.view.notes_needed
import kotlinx.android.synthetic.main.fragment_edit_goal.view.title
import kotlinx.android.synthetic.main.fragment_edit_goal.view.total
import kotlinx.android.synthetic.main.fragment_edit_goal.view.update
import com.example.cliwatch.R
import com.example.cliwatch.personalGoal.data.Goal
import com.example.cliwatch.personalGoal.data.GoalViewModel

class EditGoalFragment : Fragment() {

    private lateinit var goalViewModel: GoalViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        goalViewModel =
            ViewModelProviders.of(this).get(GoalViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_edit_goal, container, false)
        val goal = arguments?.getParcelable("goal") as Goal
        root.title.setText(goal.title)
        root.completed.setText(goal.completedDays.toString())
        root.total.setText(goal.totalDays.toString())
        root.notes_needed.isChecked = goal.hasNotes
        root.update.setOnClickListener {
            activity?.let {
                val methodManager = it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                methodManager.hideSoftInputFromWindow(it.currentFocus?.windowToken, 0)
            }
            val isValid = validateFields(listOf(root.title, root.completed, root.total))
            if (isValid) {
                goalViewModel.updateGoal(buildGoalToBeUpdated(goal.id, root))
                replaceFragment(ViewGoalsFragment())
            }
        }
        return root
    }

    private fun buildGoalToBeUpdated(goalId: Long, root: View): Goal {
        val goalToBeUpdated = Goal(
            root.title.text.toString(),
            root.completed.text.toString().toInt(),
            root.total.text.toString().toInt(),
            true,
            root.notes_needed.isChecked
        )
        goalToBeUpdated.id = goalId
        return goalToBeUpdated
    }

    private fun replaceFragment(fragment: Fragment) {
        fragmentManager?.let {
            it.beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun validateFields(fields: List<TextInputEditText>): Boolean {
        var isValid = true
        fields.forEach {
            if(it.text.isNullOrEmpty()) {
                isValid = false
                it.error = getString(R.string.required)
            }
        }
        return isValid
    }
}