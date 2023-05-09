package com.example.recipes.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipes.R
import com.example.recipes.adapters.RecipeAdapter
import com.example.recipes.data.models.DBModel
import com.example.recipes.data.models.Recipe
import com.example.recipes.databinding.RecipesMainBinding
import com.example.recipes.databinding.RecipesSavedBinding
import com.example.recipes.utils.Resource

class SavedRecipes: BaseFragment() {

    private lateinit var binding: RecipesSavedBinding
    private lateinit var adapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RecipesSavedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcview()
        observer()

    }

    private fun initRcview() {
        adapter = RecipeAdapter(this)
        binding.rcViewSaved.adapter = adapter
        binding.rcViewSaved.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observer(){
        viewModel.savedRecipes.observe(viewLifecycleOwner) { response ->
            adapter.differ.submitList(response) }
    }






    override fun openNew(id: Int) {
        val bundle= bundleOf("id" to id)
        findNavController().navigate(R.id.action_savedRecipes_to_pageRecipe, bundle)
    }

    override fun like(dish: DBModel) {
        viewModel.insert(dish)
    }


}