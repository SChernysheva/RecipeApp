package com.example.recipes.data.api

import com.example.recipes.data.modelID.modelID
import com.example.recipes.data.modelInstruction.InstrResponse
import com.example.recipes.data.modelInstruction.InstrResponseItem
import com.example.recipes.data.models.Recipe
import com.example.recipes.data.models.Res
import com.example.recipes.utils.Constant.Companion.API_KEY
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeService {
    @GET("/recipes/random")
    suspend fun getRandomRecipes(
        @Query("number") number: Int = 10,
        @Query("tags") tags: String?,
        @Query("apiKey") apiKey:String = API_KEY
    ):Response<Res>


    @GET("/recipes/{id}/information?includeNutrition=false")
    suspend fun getById(
        @Path("id") id: String,
        @Query("apiKey") apiKey:String = API_KEY
    ): Response<modelID>

    @GET("/recipes/{id}/analyzedInstructions")
    suspend fun getInstructions(
        @Path("id") id:String,
        @Query("apiKey") apiKey:String = API_KEY
    ): Response<List<InstrResponseItem>>

}