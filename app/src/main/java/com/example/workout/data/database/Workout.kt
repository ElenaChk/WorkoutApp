package com.example.workout.data.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.workout.data.model.ExerciseJson
import com.example.workout.models.ExerciseRoom

@Entity(tableName = "workout_table")
data class Workout(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "workoutId")
    val workoutId: Long = 0,
    @ColumnInfo(name = "workout_name")
    var workoutName: String,
    @ColumnInfo(name = "duration")
    var duration: String,
    @ColumnInfo(name = "exercisesList")
    var exercisesList: List<ExerciseJson>?
)