package com.example.cliwatch.data.educationData.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.cliwatch.data.educationData.entities.UserQuizScore

@Dao
interface UserQuizScoreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserQuizScore(userQuizScore: UserQuizScore): Long

    @Query("SELECT * FROM user_quiz_scores WHERE scoreID = :id")
    fun getUserQuizScore(id: Int): UserQuizScore

    @Query("SELECT * FROM user_quiz_scores WHERE userID = :userId")
    fun getScoresForUser(userId: Int): List<UserQuizScore>

    @Query("SELECT * FROM user_quiz_scores WHERE articleID = :articleId")
    fun getScoresForArticle(articleId: Int): List<UserQuizScore>

    @Update
    fun updateUserQuizScore(userQuizScore: UserQuizScore)

    @Delete
    fun deleteUserQuizScore(userQuizScore: UserQuizScore)
}
