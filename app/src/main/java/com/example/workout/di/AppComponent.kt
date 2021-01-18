package com.example.workout.di

import android.content.Context
import com.example.workout.ui.*
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DatabaseModule::class, ViewModelModule::class])
interface AppComponent{

    @Component.Factory
    interface Factory{
        fun create (@BindsInstance context: Context): AppComponent
    }
    fun inject(fragment: CategoryLibraryFragment)
    fun inject(fragment: CategoryEditorFragment)
    fun inject(fragment: ExerciseListEditorFragment)
    fun inject(fragment: ExerciseListLibraryFragment)
    fun inject(fragment: WorkoutsListFragment)
    fun inject(fragment: WorkoutEditorFragment)
}