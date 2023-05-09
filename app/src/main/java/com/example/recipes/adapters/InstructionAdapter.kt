package com.example.recipes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.R
import com.example.recipes.data.modelInstruction.InstrResponse
import com.example.recipes.data.modelInstruction.InstrResponseItem
import com.example.recipes.databinding.ListInstructionsBinding
import com.example.recipes.databinding.RecipesPageBinding

class InstructionAdapter(val list: List<InstrResponseItem>):  RecyclerView.Adapter<InstructionAdapter.ViewHolder>() {
    private lateinit var binding: ListInstructionsBinding
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding=ListInstructionsBinding.bind(view)
        fun bind(position: Int, list: List<InstrResponseItem>){
            binding.tvInstrName.text=list[position].name

            binding.rcViewListOfSteps.layoutManager=LinearLayoutManager(binding.tvInstrName.context)
            val adapter = StepsAdapter(list[position].steps)
            binding.rcViewListOfSteps.adapter=adapter


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return InstructionAdapter.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_instructions, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, list)
    }
}