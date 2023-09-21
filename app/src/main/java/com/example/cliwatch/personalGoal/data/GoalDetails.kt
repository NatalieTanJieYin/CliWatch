package com.example.cliwatch.personalGoal.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(
    foreignKeys = [ForeignKey(
        entity = Goal::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("goalId"),
        onDelete = ForeignKey.CASCADE
    )]
)
@Parcelize
abstract class GoalDetails(
    var goalId: Long = 0,
    var notes: String = "",
    var date: Date = Date()
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}