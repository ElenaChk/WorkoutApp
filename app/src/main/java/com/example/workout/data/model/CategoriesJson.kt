package com.example.workout.data.model

import com.example.workout.models.ExerciseCategory
import com.example.workout.models.ExerciseCategory.Companion.CATEGORY_ABS
import com.example.workout.models.ExerciseCategory.Companion.CATEGORY_ARMS
import com.example.workout.models.ExerciseCategory.Companion.CATEGORY_BACK
import com.example.workout.models.ExerciseCategory.Companion.CATEGORY_CALVES
import com.example.workout.models.ExerciseCategory.Companion.CATEGORY_CHEST
import com.example.workout.models.ExerciseCategory.Companion.CATEGORY_LEGS
import com.example.workout.models.ExerciseCategory.Companion.CATEGORY_SHOULDERS
import com.squareup.moshi.Json

data class CategoriesJson(
    @Json(name = "results")
    val results: List<ExerciseCategoryJson>
) {

    fun toExerciseCategory(): List<ExerciseCategory> {
        return results.map {
            when (it.categoryName) {
                CATEGORY_ABS -> ExerciseCategory.Abs(it.id, it.categoryName)
                CATEGORY_ARMS -> ExerciseCategory.Arms(it.id, it.categoryName)
                CATEGORY_BACK -> ExerciseCategory.Back(it.id, it.categoryName)
                CATEGORY_CALVES -> ExerciseCategory.Calves(it.id, it.categoryName)
                CATEGORY_CHEST -> ExerciseCategory.Chest(it.id, it.categoryName)
                CATEGORY_LEGS -> ExerciseCategory.Legs(it.id, it.categoryName)
                CATEGORY_SHOULDERS -> ExerciseCategory.Shoulders(it.id, it.categoryName)
                else -> ExerciseCategory.Empty
            }
        }
    }
}

data class ExerciseCategoryJson(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val categoryName: String
)