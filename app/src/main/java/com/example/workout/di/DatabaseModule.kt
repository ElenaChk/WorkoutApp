package com.example.workout.di

import android.content.Context
import com.example.workout.data.database.WorkoutDatabase
import com.example.workout.data.database.WorkoutDatabaseDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideWorkoutDatabase(context: Context): WorkoutDatabase{
        return WorkoutDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideWorkoutDao(workoutDatabase: WorkoutDatabase): WorkoutDatabaseDao{
        return workoutDatabase.workoutDao()
    }
}