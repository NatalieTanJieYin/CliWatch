package com.example.cliwatch.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User): Long

    @Query("SELECT * FROM users WHERE userID = :id")
    fun getUser(id: Int): User

    @Query("SELECT * FROM users")
    fun getAllUsers(): List<User>

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)
}
