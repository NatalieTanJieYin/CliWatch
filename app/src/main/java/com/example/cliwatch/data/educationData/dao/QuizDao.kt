package com.example.cliwatch.data.educationData.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.cliwatch.data.educationData.entities.Quiz

@Dao
interface QuizDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuiz(quiz: Quiz): Long

    @Query("SELECT * FROM quizzes WHERE quizID = :id")
    fun getQuiz(id: Int): Quiz

    @Query("SELECT * FROM quizzes WHERE articleID = :articleId")
    fun getQuizzesForArticle(articleId: Int): List<Quiz>

    @Update
    fun updateQuiz(quiz: Quiz)

    @Delete
    fun deleteQuiz(quiz: Quiz)
}
