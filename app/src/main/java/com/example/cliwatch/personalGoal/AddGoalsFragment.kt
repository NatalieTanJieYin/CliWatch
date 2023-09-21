package com.example.cliwatch.personalGoal

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_add_goals.view.add_goal
import kotlinx.android.synthetic.main.fragment_add_goals.view.days
import kotlinx.android.synthetic.main.fragment_add_goals.view.notes_needed
import kotlinx.android.synthetic.main.fragment_add_goals.view.title
import com.example.cliwatch.R
import com.example.cliwatch.personalGoal.data.Goal
import com.example.cliwatch.personalGoal.data.GoalViewModel


class AddGoalFragment : Fragment() {

    private lateinit var goalViewModel: GoalViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        goalViewModel =
            ViewModelProviders.of(this).get(GoalViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_add_goals, container, false)
        root.add_goal.setOnClickListener {
            activity?.let {
                val methodManager = it.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                methodManager.hideSoftInputFromWindow(it.currentFocus?.windowToken, 0)
            }
            val isValid = validateField(listOf(root.title, root.days))
            if (isValid) {
                addGoal(root.title, root.days, root)
            }
        }
        return root
    }

    private suspend fun addGoal(
        title: TextInputEditText,
        totalDays: TextInputEditText,
        root: View
    ) {
        goalViewModel.unSelectAllGoals()
        goalViewModel.insertGoal(
            Goal(
                title.text.toString(),
                0,
                totalDays.text.toString().toInt(),
                true,
                root.notes_needed.isChecked
            )
        )
        replaceFragment(GoalAddedFragment(), root)
    }

    private fun validateField(fields: List<TextInputEditText>): Boolean {
        var isValid = true
        fields.forEach {
            if (it.text.isNullOrEmpty()) {
                isValid = false
                it.error = getString(R.string.required)
            }
        }
        return isValid
    }

    private fun replaceFragment(fragment: Fragment, root: View) {
        fragmentManager?.let {
            val bundle = Bundle()
            bundle.putString("Title", root.title.text.toString())
            val transaction = it.beginTransaction()
            fragment.arguments = bundle
            transaction.replace(R.id.nav_host_fragment, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}