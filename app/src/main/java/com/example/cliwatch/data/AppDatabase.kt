package com.example.cliwatch.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cliwatch.data.educationData.dao.ArticleDao
import com.example.cliwatch.data.educationData.dao.QuizDao
import com.example.cliwatch.data.educationData.dao.UserQuizScoreDao
import com.example.cliwatch.data.educationData.entities.Article
import com.example.cliwatch.data.educationData.entities.Quiz
import com.example.cliwatch.data.educationData.entities.UserQuizScore

@Database(entities = [User::class, Article::class, Quiz::class, UserQuizScore::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun articleDao(): ArticleDao
    abstract fun quizDao(): QuizDao
    abstract fun userScoreDao(): UserQuizScoreDao

    companion object {
        // Singleton pattern for creating the database instance
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
