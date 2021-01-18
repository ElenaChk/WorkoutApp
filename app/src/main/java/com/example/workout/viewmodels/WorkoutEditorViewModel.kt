package com.example.workout.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workout.data.WorkoutRepository
import com.example.workout.data.database.Workout
import com.example.workout.data.model.ExerciseJson
import kotlinx.coroutines.launch
import javax.inject.Inject

class WorkoutEditorViewModel @Inject constructor(
    private var workoutRepository: WorkoutRepository
) : ViewModel() {

    var exerciseList: List<ExerciseJson>? = null

    var currentWorkout: Workout? = null

//    val selectedExercises = arrayListOf<ExerciseRoom>()

    fun addNewWorkout(workoutName: String, workoutDuration: String, list: List<ExerciseJson>) {
        viewModelScope.launch {
            val workout = Workout(workoutName = workoutName, duration = workoutDuration, exercisesList = list)
            Log.v("exerciseList", "add new workout ${workout.exercisesList}")
            workoutRepository.insertWorkout(workout)
        }
    }

    suspend fun getCurrentWorkout(id: Long): Workout? {
        currentWorkout = workoutRepository.getCurrentWorkout(id)
        exerciseList = currentWorkout?.exercisesList
        return currentWorkout
    }

    fun deleteWorkout(workout: Workout) {
        viewModelScope.launch {
            workoutRepository.deleteWorkout(workout)
        }
    }

    fun updateWorkout(workoutName: String, workoutDuration: String) {
        Log.d("updateWorkout", "updateWorkout")
        currentWorkout?.workoutName = workoutName
        currentWorkout?.duration = workoutDuration
        currentWorkout?.exercisesList = exerciseList
        viewModelScope.launch {
            workoutRepository.updateWorkout(currentWorkout ?: return@launch)
        }
    }
}