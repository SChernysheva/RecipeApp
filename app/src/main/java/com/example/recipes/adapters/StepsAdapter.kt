package com.example.recipes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.R
import com.example.recipes.data.modelInstruction.Step
import com.example.recipes.databinding.StepInstrItemBinding
import com.example.recipes.ui.MainActivity
import com.example.recipes.ui.fragments.MainRecipes

class StepsAdapter(val list: List<Step>): RecyclerView.Adapter<StepsAdapter.ViewHolder>() {
    private lateinit var binding: StepInstrItemBinding
    class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        private val binding=StepInstrItemBinding.bind(view)
        fun bind(list: List<Step>, position: Int){
            binding.tvInstrStepNumber.text=list[position].number.toString()
            binding.tvInstrStepTitle.text=list[position].step

            binding.rcViewInrgOfStep.setHasFixedSize(true)
            binding.rcViewInrgOfStep.layoutManager=LinearLayoutManager(binding.tvInstrStepTitle.context,
                LinearLayoutManager.HORIZONTAL, false)
            val adapter = AdapterIngredientsInstructions(list[position].ingredients)
            binding.rcViewInrgOfStep.adapter=adapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return StepsAdapter.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.step_instr_item, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list, position)
    }
}