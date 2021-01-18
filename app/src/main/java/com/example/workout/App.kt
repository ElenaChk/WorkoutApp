package com.example.workout

import android.app.Application
import com.example.workout.di.AppComponent
import com.example.workout.di.DaggerAppComponent

open class App : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}