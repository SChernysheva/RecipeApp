package com.example.recipes.utils

import androidx.room.TypeConverter
import com.example.recipes.data.api.RecipeService
import com.example.recipes.data.api.retrofit
import com.example.recipes.data.models.DBModel
import com.example.recipes.data.models.Recipe
import com.example.recipes.data.models.Res
import com.example.recipes.ui.MainActivity
import com.example.recipes.ui.RecipeViewModel
import retrofit2.Response
import java.util.stream.Collectors

class Converter {

    @TypeConverter
    fun booleanToInt(value: Boolean): Int {
        if(!value) return 0
        else return 1
    }

    @TypeConverter
    fun IntToBoolean(value: Int): Boolean{
        return value==1
    }

    @TypeConverter
    fun fromDishToString(value: List<String>):String{
        return value.stream().collect(Collectors.joining(","))
    }

    @TypeConverter
    fun fromStrToDish(value: String): List<String> {
        return value.split(",")
    }


    }

