package com.example.workout.data.database

import androidx.room.TypeConverter
import com.example.workout.data.model.ExerciseJson
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ExerciseConverters {
    @TypeConverter
    fun listToJson(list: List<ExerciseJson>): String = Gson().toJson(list)
    @TypeConverter
    fun jsonToList(json: String): List<ExerciseJson>{
        val type: Type = object : TypeToken<List<ExerciseJson>>(){}.type
        return Gson().fromJson(json, type)
    }
}
