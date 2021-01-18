package com.example.workout.models

sealed class ExerciseCategory {
    data class Abs(val id: Int, val name: String) : ExerciseCategory()
    data class Arms(val id: Int, val name: String) : ExerciseCategory()
    data class Back(val id: Int, val name: String) : ExerciseCategory()
    data class Calves(val id: Int, val name: String) : ExerciseCategory()
    data class Chest(val id: Int, val name: String) : ExerciseCategory()
    data class Legs(val id: Int, val name: String) : ExerciseCategory()
    data class Shoulders(val id: Int, val name: String) : ExerciseCategory()
    object Empty : ExerciseCategory()

    companion object {
        const val CATEGORY_ABS = "Abs"
        const val CATEGORY_ARMS = "Arms"
        const val CATEGORY_BACK = "Back"
        const val CATEGORY_CALVES = "Calves"
        const val CATEGORY_CHEST = "Chest"
        const val CATEGORY_LEGS = "Legs"
        const val CATEGORY_SHOULDERS = "Shoulders"
    }
}