package com.example.workout.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.workout.viewmodels.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CategoryViewModel::class)
    internal abstract fun bindCategoryViewModel(viewModel: CategoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExerciseListViewModel::class)
    internal abstract fun bindExerciseListViewModel(viewModel: ExerciseListViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WorkoutsListViewModel::class)
    internal abstract fun bindWorkoutsListViewModel(viewModel: WorkoutsListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WorkoutEditorViewModel::class)
    internal abstract fun bindWorkoutEditorViewModel(viewModel: WorkoutEditorViewModel): ViewModel
}