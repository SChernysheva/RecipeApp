package com.example.recipes.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.recipes.data.models.DBModel
import com.example.recipes.utils.Converter

@Database(entities = [DBModel::class], version = 1)
@TypeConverters(Converter::class)
abstract class DataBaseSaved: RoomDatabase() {

    abstract fun getRecipeDao(): RecipeDao

    companion object{
        @Volatile
        private var instance: DataBaseSaved? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDataBase(context).also { instance = it}
        }

        private fun createDataBase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                DataBaseSaved::class.java,
                "recipe_db"
            ).build()
    }
}