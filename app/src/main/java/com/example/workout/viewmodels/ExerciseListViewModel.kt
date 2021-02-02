package com.example.workout.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workout.data.model.ExerciseJson
import com.example.workout.data.retrofit.ApiService
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExerciseListViewModel @Inject constructor(private val apiService: ApiService) : ViewModel() {

    companion object {
        const val ENG_LANG_ID = 2
    }

    val exercises: LiveData<List<ExerciseJson?>>
        get() = _exercises
    private val _exercises = MutableLiveData<List<ExerciseJson?>>()

    val selectedExercises = mutableListOf<ExerciseJson>()

    fun getListExercise(categoryId: Int) {
        viewModelScope.launch {
            val exercises = apiService.getExercise(ENG_LANG_ID, categoryId, 200).results
            _exercises.value = exercises
        }
    }
    fun setSelectedExercises(list: MutableList<ExerciseJson>){
        selectedExercises.addAll(list)
    }

    fun clearSelectedExercises(){
        selectedExercises.clear()
    }

    fun clearEvent() {
        _exercises.value = emptyList()
    }
}


