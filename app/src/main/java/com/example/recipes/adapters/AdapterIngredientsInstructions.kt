package com.example.recipes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipes.R
import com.example.recipes.data.modelID.ExtendedIngredient
import com.example.recipes.data.modelInstruction.Ingredient
import com.example.recipes.databinding.ItemIngredientBinding

class AdapterIngredientsInstructions(val list:List<Ingredient>): RecyclerView.Adapter<AdapterIngredientsInstructions.ViewHolder>() {
    private lateinit var binding: ItemIngredientBinding
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = ItemIngredientBinding.bind(view)
        fun bind(position: Int, list: List<Ingredient>) {
            binding.tvNameIngr.text=list[position].name
            Glide.with(itemView.context)
                .load("https://spoonacular.com/cdn/ingredients_100x100/" + list[position].image)
                .into(binding.imViewIngr)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return AdapterIngredientsInstructions.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_ingredient, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, list)
    }
}