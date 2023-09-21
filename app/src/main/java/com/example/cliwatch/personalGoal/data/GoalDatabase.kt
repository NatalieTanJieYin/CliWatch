package com.example.cliwatch.personalGoal.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Goal::class, GoalDetails::class], version = 1)
@TypeConverters(Converters::class)
abstract class GoalDatabase : RoomDatabase() {

    abstract fun goalDAO(): GoalDAO

    abstract fun goalDetailsDAO(): GoalDetailsDAO

    companion object {
        private var instance: GoalDatabase? = null

        fun getInstance(context: Context): GoalDatabase {
            if (instance == null) {
                synchronized(GoalDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GoalDatabase::class.java, "goals_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance!!
        }

        fun destroyInstance() {
            instance = null
        }
    }
}