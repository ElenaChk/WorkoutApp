package com.example.workout.data

import com.example.workout.data.retrofit.ApiService
import com.example.workout.models.ExerciseCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getCategories(): List<ExerciseCategory> = withContext(Dispatchers.IO) {
        apiService.getCategories().toExerciseCategory()
    }

}