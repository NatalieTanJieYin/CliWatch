package com.example.cliwatch.personalGoal.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class GoalViewModel(application: Application) : AndroidViewModel(application) {
    private var goalRepository: GoalRepository = GoalRepository(application)

    private var goalDetailsRepository: GoalDetailsRepository =
        GoalDetailsRepository(application)

    var goals: LiveData<List<Goal>> = goalRepository.getAllGoals()

    fun getOrderedGoals(
        goalsFromDB: List<Goal>?
    ): MutableList<Goal> {
        val goals = mutableListOf<Goal>()
        val selectedGoal = goalsFromDB?.find { it.isSelected }
        selectedGoal?.let {
            goals.add(0, selectedGoal)
        }
        val otherGoals = goalsFromDB?.filter { !it.isSelected }
        otherGoals?.let {
            goals.addAll(otherGoals)
        }
        return goals
    }

    fun goalDetails(goalId: Long): LiveData<List<GoalDetails>> =
        goalDetailsRepository.getGoalDetails(goalId)

    val text = MutableLiveData<String>().apply {
        value = goals.value?.find { it.isSelected }?.completedDays.toString()
    }

    suspend fun updateGoal(goal: Goal?) {
        goal?.let { goalRepository.updateGoal(goal) }
    }

    suspend fun insertGoal(goal: Goal) {
        goalRepository.insertGoal(goal)
    }

    suspend fun insertGoalDetails(goalDetails: GoalDetails) {
        goalDetailsRepository.insertGoalDetails(goalDetails)
    }

    suspend fun updateGoalDetails(goalDetails: GoalDetails) {
        goalDetailsRepository.updateGoalDetails(goalDetails)
    }

    suspend fun unSelectAllGoals() {
        goalRepository.unSelectAllGoals()
    }

    suspend fun deleteGoal(goal: Goal) {
        goalRepository.deleteGoal(goal)
    }
}