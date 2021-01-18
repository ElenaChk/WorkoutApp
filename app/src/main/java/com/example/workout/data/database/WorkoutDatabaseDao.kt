package com.example.workout.data.database

import androidx.room.*

@Dao
interface WorkoutDatabaseDao {

    @Query("SELECT * FROM workout_table")
    suspend fun getAllWorkouts(): List<Workout>

    @Query("SELECT * FROM workout_table WHERE workoutId == :id")
    suspend fun getWorkout(id: Long): Workout

    @Query("DELETE FROM workout_table")
    suspend fun deleteAll()

    @Insert
    suspend fun insert(workout: Workout)

    @Update
    suspend fun update(workout: Workout)

    @Delete
    suspend fun deleteWorkout(workout: Workout)
}