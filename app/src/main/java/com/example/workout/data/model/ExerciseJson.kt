package com.example.workout.data.model

import com.squareup.moshi.Json

data class ExerciseListJson(
    @Json(name = "results")
    val results: List<ExerciseJson?>
)

data class ExerciseJson(
    @Json(name = "id")
    val id: Long?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "images")
    val exerciseImage: List<ImageJson?>,
    @Json(name = "language")
    val language: LanguageJson,
    @Json(name = "category")
    val category: CategoryJson,
)

data class ImageJson(
    @Json(name = "image")
    val imageUrl: String?,
    @Json(name = "is_main")
    val isMain: Boolean
)

data class LanguageJson(
    @Json(name = "id")
    val LangId: Int?
)

data class CategoryJson(
    @Json(name = "id")
    val categoryId: Int?
)