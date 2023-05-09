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
import com.example.recipes.utils.Converter
import com.example.recipes.utils.Resource

class MainRecipes: BaseFragment() {

    private lateinit var binding: RecipesMainBinding
    private lateinit var adapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RecipesMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcview()
        observer()

        with(binding) {

            bread.setOnClickListener {
            viewModel.tag = "bread"
            viewModel.getRecipes(viewModel.tag)
        }

            soup.setOnClickListener {
                viewModel.tag="soup"
                viewModel.getRecipes(viewModel.tag)
            }
            dessert.setOnClickListener {
                viewModel.tag="dessert"
                viewModel.getRecipes(viewModel.tag)
            }

            salad.setOnClickListener {
                viewModel.tag="salad"
                viewModel.getRecipes(viewModel.tag)
            }
            sideDish.setOnClickListener {
                viewModel.tag="side dish"
                viewModel.getRecipes(viewModel.tag)
            }
            random.setOnClickListener {
                viewModel.getRecipes("")
            }
            mainCourse.setOnClickListener {
                viewModel.tag="main course"
                viewModel.getRecipes(viewModel.tag)
            }
            breakfast.setOnClickListener {
                viewModel.tag="breakfast"
                viewModel.getRecipes(viewModel.tag)
            }
        }

    }


    override fun openNew(id: Int) {
        val bundle=bundleOf("id" to id)
        findNavController().navigate(R.id.action_mainRecipes_to_pageRecipe, bundle)
    }

    override fun like(dish: DBModel) {
        viewModel.insert(dish)

    }

    private fun initRcview() {
        adapter = RecipeAdapter(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observer(){
        viewModel.recipes.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hidePB()
                    response.data?.let {
                        adapter.differ
                            .submitList(viewModel.fromRecipeToDBModel(it.recipes)) }
                }
                is Resource.Loading -> showPB()
                is Resource.Error -> {hidePB()}
            }
        }
    }


    private fun hidePB(){ binding.progressBar.visibility= View.INVISIBLE}
    private fun showPB(){ binding.progressBar.visibility= View.VISIBLE}
}