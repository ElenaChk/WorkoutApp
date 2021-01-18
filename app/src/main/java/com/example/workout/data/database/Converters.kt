package com.example.workout.data.database

import androidx.room.TypeConverter
import com.example.workout.data.model.ExerciseJson
import com.example.workout.data.model.ImageJson
import com.example.workout.models.ExerciseRoom
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
class ImageConverters{
    @TypeConverter
    fun listToJson(list: List<ImageJson>): String = Gson().toJson(list)
    @TypeConverter
    fun jsonToList(json: String) = Gson().fromJson(json, Array<ImageJson>::class.java).toList()
}