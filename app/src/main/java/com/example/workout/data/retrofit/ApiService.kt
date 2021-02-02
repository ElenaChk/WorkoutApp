package com.example.workout.data.retrofit

import com.example.workout.data.model.CategoriesJson
import com.example.workout.data.model.ExerciseListJson
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("exerciseinfo")
    suspend fun getExercise(@Query("language") language: Int,
                            @Query("category") category: Int,
                            @Query("limit") limit: Int): ExerciseListJson

    @GET("exercisecategory")
    suspend fun getCategories(): CategoriesJson
}
