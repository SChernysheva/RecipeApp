package com.example.recipes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.recipes.R
import com.example.recipes.data.database.DataBaseSaved
import com.example.recipes.databinding.ActivityMainBinding
import com.example.recipes.repository.RecipeRepository


class MainActivity : AppCompatActivity() {
    lateinit var viewModel: RecipeViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recipeRepository = RecipeRepository(DataBaseSaved(this))
        val viewModelProviderFactory = RecipeViewModelProviderFactory(recipeRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(RecipeViewModel::class.java)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.recipesNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNav.setupWithNavController(navController)






    }
}

