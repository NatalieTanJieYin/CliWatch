package com.example.cliwatch.data.educationData.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class Article(
    @PrimaryKey(autoGenerate = true)
    val articleID: Int,
    val title: String,
    val content: String
    // Add other fields like `author`, `datePublished` if required
)
