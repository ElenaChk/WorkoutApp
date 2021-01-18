package com.example.workout.viewmodels

import android.util.Log
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
            val exercises = apiService.getExercise(100).results
            val list = mutableListOf<ExerciseJson>()
            for (item in exercises) {
                val isLanguageENG = item?.language?.LangId == ENG_LANG_ID
                val isForThisCategory = item?.category?.categoryId == categoryId
                if (item != null && isLanguageENG && isForThisCategory) {
                    list.add(item)
                }
            }
            _exercises.value = list
        }
    }
    fun setSelectedExercises(list: MutableList<ExerciseJson>){
        selectedExercises.addAll(list)
        Log.v("ExerciseListViewModel", "${selectedExercises.size}")

    }

    fun clearSelectedExercises(){
        selectedExercises.clear()
    }

    fun clearEvent() {
        _exercises.value = emptyList()
    }
}


