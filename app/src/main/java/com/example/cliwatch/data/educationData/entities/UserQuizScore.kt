package com.example.cliwatch.data.educationData.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.cliwatch.data.User

@Entity(tableName = "user_quiz_scores",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["userID"], childColumns = ["userID"]),
        ForeignKey(entity = Article::class, parentColumns = ["articleID"], childColumns = ["articleID"])
    ])
data class UserQuizScore(
    @PrimaryKey(autoGenerate = true)
    val scoreID: Int,
    val userID: Int,
    val articleID: Int,
    val score: Int
)
