package com.example.cliwatch.personalGoal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_view_goals.view.goal_list
import kotlinx.android.synthetic.main.fragment_view_goals.view.no_goals
import com.example.cliwatch.R
import com.example.cliwatch.personalGoal.data.Goal
import com.example.cliwatch.personalGoal.EditGoalFragment
import com.example.cliwatch.personalGoal.GoalDetailsFragment
import com.example.cliwatch.personalGoal.data.GoalViewModel
import com.example.cliwatch.personalGoal.data.ViewGoalAdapter

class ViewGoalsFragment : Fragment(), ViewGoalNotifier {

    private lateinit var goalsViewModel: GoalViewModel
    private lateinit var adapter: ViewGoalAdapter

    override fun deleteGoal(goal: Goal, position: Int) {
        AlertDialog.Builder(context!!)
            .setTitle(R.string.delete)
            .setMessage(R.string.confirmation_message)
            .setPositiveButton(R.string.ok) { _, _ ->
                goalsViewModel.deleteGoal(goal)
                adapter.deleteSuccess(position)
            }.setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    override fun editGoal(goal: Goal) {
        val bundle = Bundle()
        bundle.putParcelable("goal", goal)
        replaceFragment(EditGoalFragment(), bundle)
    }

    override fun updateGoal(goal: Goal) {
        goalsViewModel.updateGoal(goal)
    }

    override fun onItemSelected(goal: Goal) {
        val bundle = Bundle()
        bundle.putParcelable("goal", goal)
        replaceFragment(GoalDetailsFragment(), bundle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        goalsViewModel =
            ViewModelProviders.of(this).get(GoalViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_view_goals, container, false)
        val recyclerView = root.goal_list
        goalsViewModel.goals.observe(this, Observer {
            val orderedGoals = goalsViewModel.getOrderedGoals(it)
            toggleVisibility(root.no_goals, root.goal_list, orderedGoals.isEmpty())
            adapter = ViewGoalAdapter(
                activity,
                orderedGoals as MutableList<Goal>?
                    ?: mutableListOf(),
                this
            )
            recyclerView.adapter = adapter
        })
        recyclerView.layoutManager = LinearLayoutManager(activity)
        return root
    }

    private fun replaceFragment(fragment: Fragment, bundle: Bundle) {
        fragmentManager?.let {
            fragment.arguments = bundle
            it.beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun toggleVisibility(
        noNotes: View,
        recyclerView: RecyclerView,
        isEmpty: Boolean
    ) {
        noNotes.visibility = if (isEmpty) View.VISIBLE else View.GONE
        recyclerView.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }
}

interface ViewGoalNotifier {
    fun deleteGoal(goal: Goal, position: Int)

    fun editGoal(goal: Goal)

    fun updateGoal(goal: Goal)

    fun onItemSelected(goal: Goal)
}