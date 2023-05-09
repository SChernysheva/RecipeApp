package com.example.recipes.repository

import com.example.recipes.data.api.retrofit
import com.example.recipes.data.database.DataBaseSaved
import com.example.recipes.data.models.DBModel

class RecipeRepository(
    val db: DataBaseSaved
) {
    suspend fun getRandomRecipes(tag: String?) =
        retrofit.apiService.getRandomRecipes(tags = tag)


    suspend fun getRecipesById(id:Int) =
        retrofit.apiService.getById(id.toString())



    suspend fun insert(dish: DBModel){
        db.getRecipeDao().insert(dish)
    }

    fun getSavedRecipes() = db.getRecipeDao().getAllSavedRecipes()

    fun getSavedRecipesById(title: String) = db.getRecipeDao().getRecipesById(title)

    suspend fun delete(dish: DBModel) {
        db.getRecipeDao().deleteRecipe(dish.title)
    }

    suspend fun getInstruction(id: Int) =
        retrofit.apiService.getInstructions(id.toString())
}