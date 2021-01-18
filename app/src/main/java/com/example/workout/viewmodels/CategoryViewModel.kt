package com.example.workout.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workout.R
import com.example.workout.data.CategoryRepository
import com.example.workout.models.CategoryWithImage
import com.example.workout.models.ExerciseCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryViewModel @Inject constructor(private val categoryRepository: CategoryRepository) : ViewModel() {

    val categoriesList: LiveData<List<CategoryWithImage>>
        get() = _categoriesList
    private val _categoriesList = MutableLiveData<List<CategoryWithImage>>()

    fun getCategoriesList() {
        viewModelScope.launch {
            val categories = categoryRepository.getCategories()
            val categoriesWithImages = mutableListOf<CategoryWithImage>()

            withContext(Dispatchers.Default) {
                for (item in categories) {
                    val category = when (item) {
                        is ExerciseCategory.Abs -> CategoryWithImage(item.id, item.name, R.drawable.category_abs)
                        is ExerciseCategory.Arms -> CategoryWithImage(item.id, item.name, R.drawable.category_arms)
                        is ExerciseCategory.Back -> CategoryWithImage(item.id, item.name, R.drawable.category_back)
                        is ExerciseCategory.Calves -> CategoryWithImage(item.id, item.name, R.drawable.category_calves)
                        is ExerciseCategory.Chest -> CategoryWithImage(item.id, item.name, R.drawable.category_chest)
                        is ExerciseCategory.Legs -> CategoryWithImage(item.id, item.name, R.drawable.category_legs)
                        is ExerciseCategory.Shoulders -> CategoryWithImage(item.id, item.name, R.drawable.category_shoulders)
                        ExerciseCategory.Empty -> CategoryWithImage(-1, "", 0)
                    }
                    categoriesWithImages.add(category)
                }
            }

            _categoriesList.value = categoriesWithImages
        }
    }
}