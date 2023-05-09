package com.example.recipes.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.recipes.ui.MainActivity
import com.example.recipes.adapters.RecipeAdapter
import com.example.recipes.ui.RecipeViewModel

abstract class BaseFragment: Fragment(), RecipeAdapter.Listener {
    lateinit var viewModel: RecipeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel= (activity as MainActivity).viewModel
        super.onViewCreated(view, savedInstanceState)
    }


}
