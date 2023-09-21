package com.example.cliwatch.personalGoal.data

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GoalRepository(application: Application) {
    private val goalDAO: GoalDAO

    init {
        val database: GoalDatabase = GoalDatabase.getInstance(application.applicationContext)
        goalDAO = database.goalDAO()
    }

    fun getAllGoals(): LiveData<List<Goal>> {
        return goalDAO.getAllGoals()
    }

    suspend fun insertGoal(goal: Goal) {
        withContext(Dispatchers.IO) {
            goalDAO.insertGoal(goal)
        }
    }

    suspend fun updateGoal(goal: Goal) {
        withContext(Dispatchers.IO) {
            goalDAO.updateGoal(goal)
        }
    }

    suspend fun unSelectAllGoals() {
        withContext(Dispatchers.IO) {
            goalDAO.unSelectAllGoals(false)
        }
    }

    suspend fun deleteGoal(goal: Goal) {
        withContext(Dispatchers.IO) {
            goalDAO.deleteGoal(goal)
        }
    }
}
