package com.example.cliwatch.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val userID: Int,
    val username: String
    // Add other fields if necessary
)
