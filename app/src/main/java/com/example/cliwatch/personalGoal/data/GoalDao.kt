package com.example.cliwatch.personalGoal.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface GoalDAO {

    @Query("SELECT * FROM Goal order by id")
    fun getAllGoals(): LiveData<List<Goal>>

    @Insert(onConflict = REPLACE)
    fun insertGoal(goal: Goal): Long

    @Update
    fun updateGoal(goal: Goal)

    @Query("UPDATE Goal SET isSelected = :isSelected")
    fun unSelectAllGoals(isSelected: Boolean)

    @Delete
    fun deleteGoal(goal: Goal)
}