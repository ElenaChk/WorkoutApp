package com.example.workout.models

import androidx.room.ColumnInfo
import com.example.workout.data.model.CategoryJson
import com.example.workout.data.model.ImageJson
import com.example.workout.data.model.LanguageJson

data class ExerciseRoom(
    val id: Long?,
    val name: String?,
    val description: String?)