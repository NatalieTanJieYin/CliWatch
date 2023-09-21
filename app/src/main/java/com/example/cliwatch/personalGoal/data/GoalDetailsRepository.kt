package com.example.cliwatch.personalGoal.data

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GoalDetailsRepository(application: Application) {
    private val goalDetailsDAO: GoalDetailsDAO

    init {
        val database: GoalDatabase = GoalDatabase.getInstance(application.applicationContext)
        goalDetailsDAO = database.goalDetailsDAO()
    }

    fun getGoalDetails(goalId: Long): LiveData<List<GoalDetails>> {
        return goalDetailsDAO.getGoalDetails(goalId)
    }

    suspend fun insertGoalDetails(goalDetails: GoalDetails) {
        withContext(Dispatchers.IO) {
            goalDetailsDAO.insertGoalDetails(goalDetails)
        }
    }

    suspend fun updateGoalDetails(goalDetails: GoalDetails) {
        withContext(Dispatchers.IO) {
            goalDetailsDAO.updateGoalDetails(goalDetails)
        }
    }
}
