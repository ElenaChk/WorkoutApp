package com.example.workout.data

import com.example.workout.data.database.Workout
import com.example.workout.data.database.WorkoutDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkoutRepository @Inject constructor(
    private val workoutDatabaseDao: WorkoutDatabaseDao
) {

    suspend fun getMyWorkouts(): List<Workout> = withContext(Dispatchers.IO) {
        workoutDatabaseDao.getAllWorkouts()
    }

    suspend fun getCurrentWorkout(id: Long): Workout = withContext(Dispatchers.IO) {
        workoutDatabaseDao.getWorkout(id)
    }

    suspend fun insertWorkout(workout: Workout) = withContext(Dispatchers.IO) {
        workoutDatabaseDao.insert(workout)
    }

    suspend fun updateWorkout(workout: Workout) = withContext(Dispatchers.IO) {
        workoutDatabaseDao.update(workout)
    }

    suspend fun deleteWorkout(workout: Workout) = withContext(Dispatchers.IO) {
        workoutDatabaseDao.deleteWorkout(workout)
    }
}