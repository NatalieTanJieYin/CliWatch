package com.example.cliwatch.personalGoal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_goal_added.view.goal_title
import kotlinx.android.synthetic.main.fragment_goal_added.view.add_goal
import kotlinx.android.synthetic.main.fragment_goal_added.view.home
import com.example.cliwatch.R
import com.example.cliwatch.personalGoal.GoalFragment
import com.example.cliwatch.personalGoal.data.GoalViewModel

class GoalAddedFragment : Fragment() {

    private lateinit var goalViewModel: GoalViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        goalViewModel =
            ViewModelProviders.of(this).get(GoalViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_goal_added, container, false)
        root.goal_title.text =
            arguments?.getString("Title")?.plus(" " + getString(R.string.added))
                ?: getString(R.string.goal_added)
        root.add_goal.setOnClickListener {
            replaceFragment(AddGoalFragment())
        }
        root.home.setOnClickListener {
            replaceFragment(GoalFragment())
        }
        return root
    }

    private fun replaceFragment(fragment: Fragment) {
        fragmentManager?.let {
            it.beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}