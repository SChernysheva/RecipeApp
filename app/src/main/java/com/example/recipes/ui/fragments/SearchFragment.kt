package com.example.recipes.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipes.R
import com.example.recipes.adapters.RecipeAdapter
import com.example.recipes.data.api.retrofit
import com.example.recipes.data.models.DBModel
import com.example.recipes.data.models.Recipe
import com.example.recipes.databinding.RecipesMainBinding
import com.example.recipes.databinding.SearchFragBinding
import com.example.recipes.utils.Resource
import kotlinx.coroutines.*
import retrofit2.Response

class SearchFragment: BaseFragment() {
    lateinit var binding: SearchFragBinding
    lateinit var adapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SearchFragBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcview()
        hidePB()

        var job: Job? = null
        binding.editText.addTextChangedListener {
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                if (it.toString().isNotEmpty()) {
                    viewModel.getSearchingRecipes(it.toString())
                }
            }
        }
        observer()

    }


    override fun openNew(id: Int) {
        val bundle= bundleOf("id" to id)
        findNavController().navigate(R.id.action_searchFragment_to_pageRecipe, bundle)
    }

    override fun like(dish: DBModel) {
         viewModel.insert(dish)
    }


    private fun initRcview() {
        adapter = RecipeAdapter(this)
        binding.recyclerViewSearch.adapter = adapter
        binding.recyclerViewSearch.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observer(){
        viewModel.searchingRecipes_.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hidePB()
                    response.data.let {
                        if (it != null) {
                            adapter.differ.submitList(viewModel.fromRecipeToDBModel(it.recipes))
                        }
                    }
                }
                is Resource.Loading -> {showPB()}
                is Resource.Error -> {hidePB()}
            }
        }
    }


    private fun hidePB(){ binding.progressBar2.visibility= View.INVISIBLE}
    private fun showPB(){ binding.progressBar2.visibility= View.VISIBLE}


}