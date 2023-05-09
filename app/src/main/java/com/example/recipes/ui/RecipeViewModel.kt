package com.example.recipes.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipes.data.modelID.modelID
import com.example.recipes.data.modelInstruction.InstrResponseItem
import com.example.recipes.data.models.DBModel
import com.example.recipes.data.models.Recipe
import com.example.recipes.data.models.Res
import com.example.recipes.repository.RecipeRepository
import com.example.recipes.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class RecipeViewModel(
    val recipeRepository: RecipeRepository,
): ViewModel() {

    val recipes: MutableLiveData<Resource<Res>> = MutableLiveData()
    val savedRecipes: LiveData<List<DBModel>> = recipeRepository.getSavedRecipes()
    val searchingRecipes_: MutableLiveData<Resource<Res>> = MutableLiveData()
    var saved: Long? = null
    var tag: String? = null
    var page: Resource<modelID>? = null
    var instructions: Resource<List<InstrResponseItem>>? = null

    init {
        getRecipes(tag)
    }
    fun insert(dish:DBModel) {
        getLike(dish.title)
        while (saved==null) {}
        if (saved!!<=0) {
            dish.isLike=true
            saved=null
            viewModelScope.launch {
                recipeRepository.insert(dish)
            }
            return
        }
        if (saved!! >=1){
            dish.isLike=false
            saved=null
            viewModelScope.launch {
                recipeRepository.delete(dish)
            }
            return
        }
    }
    fun getRecipes(tag: String?){
        viewModelScope.launch {
            recipes.postValue(Resource.Loading())
            val response = recipeRepository.getRandomRecipes(tag)
            recipes.postValue(handleResponce(response))
        }
    }
    fun getInstructionsById(id: Int, listener: changeListenerInstr) {
        viewModelScope.launch {
            try {
                instructions=Resource.Loading()
                listener.instrChanged(Resource.Loading())
                val response = recipeRepository.getInstruction(id)
                instructions= handleResponceInstr(response)
                listener.instrChanged(instructions!!)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getSearchingRecipes(query: String){
        viewModelScope.launch {
            searchingRecipes_.postValue(Resource.Loading())
            val response = recipeRepository.getRandomRecipes(query)
            searchingRecipes_.postValue(handleResponceSearch(response))
        }
    }

    fun getRecipeById(id:Int, listener: changeListener){
        viewModelScope.launch {
            try {
                page=Resource.Loading()
                listener.pageChanged(page!!)
                val responce = recipeRepository.getRecipesById(id)
                page=handleResponceId(responce)
                listener.pageChanged(page!!)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun getLike(title: String){
        viewModelScope.launch(Dispatchers.IO){
            saved=recipeRepository.getSavedRecipesById(title)
        }
    }



    private fun handleResponce(response: Response<Res>): Resource<Res> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleResponceInstr(response: Response<List<InstrResponseItem>>): Resource<List<InstrResponseItem>> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleResponceSearch(response: Response<Res>): Resource<Res> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }


    private fun handleResponceId(response: Response<modelID>): Resource<modelID> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }


    interface changeListener{
        fun pageChanged(page:Resource<modelID>)
    }

    interface changeListenerInstr{
        fun instrChanged(instr:Resource<List<InstrResponseItem>>)
    }

    fun fromRecipeToDBModel(list: List<Recipe>): List<DBModel>{
        var list2= ArrayList<DBModel>()
        list.forEach {
            val elem: DBModel = DBModel(
                aggregateLikes = it.aggregateLikes,
                cookingMinutes = it.cookingMinutes,
                id = it.id,
                image = it.image,
                instructions = it.instructions,
                preparationMinutes = it.preparationMinutes,
                readyInMinutes = it.readyInMinutes,
                servings = it.servings,
                sourceName = it.sourceName,
                sourceUrl = it.sourceUrl,
                summary = it.summary,
                title = it.title,
                isLike = false
            )
            list2.add(elem)
        }
        return list2.toList()
    }






}