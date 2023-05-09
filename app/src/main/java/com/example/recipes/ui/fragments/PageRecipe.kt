package com.example.recipes.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.recipes.R
import com.example.recipes.adapters.IngredientAdapter
import com.example.recipes.adapters.InstructionAdapter
import com.example.recipes.data.modelID.ExtendedIngredient
import com.example.recipes.data.modelID.modelID
import com.example.recipes.data.modelInstruction.InstrResponse
import com.example.recipes.data.modelInstruction.InstrResponseItem
import com.example.recipes.data.models.DBModel
import com.example.recipes.data.models.Recipe
import com.example.recipes.databinding.RecipesMainBinding
import com.example.recipes.databinding.RecipesPageBinding
import com.example.recipes.ui.RecipeViewModel
import com.example.recipes.utils.Resource

class PageRecipe: BaseFragment(), RecipeViewModel.changeListener, RecipeViewModel.changeListenerInstr {
    lateinit var adapter: IngredientAdapter
    lateinit var adapter2: InstructionAdapter
    lateinit var binding: RecipesPageBinding
    var saved: Long? = null
    val args: PageRecipeArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RecipesPageBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun openNew(id: Int) {}
    override fun like(dish: DBModel) {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt("id")
        if (id != null) {
            viewModel.getInstructionsById(id, this)
            viewModel.getRecipeById(id, this)
        }
    }

    private fun initRC(list: List<ExtendedIngredient>) {
        adapter = IngredientAdapter(list)
        binding.rcViewIngr.adapter = adapter
        binding.rcViewIngr.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL, false
        )
    }
    private fun initRC2(list: List<InstrResponseItem>) {
        adapter2=InstructionAdapter(list)
        binding.recViewInstuctions.adapter = adapter2
        binding.recViewInstuctions.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL, false
        )
    }


    override fun pageChanged(page: Resource<modelID>) {
        when (page) {
            is Resource.Success -> {
                binding.sview.visibility=View.VISIBLE
                binding.pb.visibility=View.INVISIBLE
                val page = viewModel.page?.data
                val list = page!!.extendedIngredients

                initRC(list)


                binding.tvName.text = page.title
                binding.summary.text = page.summary
                binding.tvSource.text = page.sourceName
                Glide.with(requireActivity()).load(page.image).into(binding.imView)

            }
            is Resource.Loading-> {
                binding.sview.visibility=View.INVISIBLE
                binding.pb.visibility=View.VISIBLE

            }
            is Resource.Error -> {binding.pb.visibility=View.INVISIBLE}
        }
    }

    override fun instrChanged(instr: Resource<List<InstrResponseItem>>) {
        when (instr){
            is Resource.Success -> {
                initRC2(instr.data!!)
            }
            is Resource.Loading -> {}
            is Resource.Error -> {}
        }
    }
}