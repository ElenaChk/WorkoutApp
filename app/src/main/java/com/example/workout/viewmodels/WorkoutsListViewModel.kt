package com.example.workout.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workout.data.WorkoutRepository
import com.example.workout.data.database.Workout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WorkoutsListViewModel @Inject constructor(private val workoutRepository: WorkoutRepository) : ViewModel() {

    val workouts: LiveData<List<Workout>>
        get() = _workouts
    private val _workouts = MutableLiveData<List<Workout>>()

    fun getWorkouts() {
        viewModelScope.launch {
            _workouts.value = workoutRepository.getMyWorkouts()
        }
    }
}