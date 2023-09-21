package com.example.cliwatch.data.educationData.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "quizzes",
    foreignKeys = [ForeignKey(entity = Article::class, parentColumns = ["articleID"], childColumns = ["articleID"])])
data class Quiz(
    @PrimaryKey(autoGenerate = true)
    val quizID: Int,
    val articleID: Int,
    val question: String,
    val option1: String,
    val option2: String,
    val option3: String,
    val option4: String,
    val correctOption: Int
)
