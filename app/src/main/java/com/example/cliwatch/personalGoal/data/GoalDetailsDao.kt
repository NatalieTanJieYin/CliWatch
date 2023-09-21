package com.example.cliwatch.personalGoal.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Update

@Dao
interface GoalDetailsDAO {

    @Query("SELECT * FROM GoalDetails where goalId = :goalId")
    fun getGoalDetails(goalId: Long): LiveData<List<GoalDetails>>

    @Insert(onConflict = REPLACE)
    fun insertGoalDetails(goalDetails: GoalDetails): Long

    @Update
    fun updateGoalDetails(goalDetails: GoalDetails)
}