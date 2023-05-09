package com.example.recipes.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.recipes.data.models.DBModel

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dish: DBModel)

    @Query("SELECT * FROM recipe")
    fun getAllSavedRecipes(): LiveData<List<DBModel>>

    @Query("SELECT * FROM recipe WHERE title = :title")
    fun getRecipesById(title: String): Long

    @Query("DELETE from recipe WHERE title = :title")
    suspend fun deleteRecipe(title: String)

}