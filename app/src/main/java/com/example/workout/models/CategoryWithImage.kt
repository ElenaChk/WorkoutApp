package com.example.workout.models

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

data class CategoryWithImage(
    val id: Int,
    val categoryName: String,
    @DrawableRes val categoryImage: Int
)