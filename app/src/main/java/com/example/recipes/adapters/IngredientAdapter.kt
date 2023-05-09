package com.example.recipes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipes.R
import com.example.recipes.data.modelID.ExtendedIngredient
import com.example.recipes.data.modelID.modelID
import com.example.recipes.data.models.Recipe
import com.example.recipes.databinding.ItemIngredientBinding
import com.example.recipes.databinding.RecipeItemBinding
import java.util.zip.Inflater

class IngredientAdapter(val list:List<ExtendedIngredient>): RecyclerView.Adapter<IngredientAdapter.ViewHolder>() {
    private lateinit var binding: ItemIngredientBinding
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = ItemIngredientBinding.bind(view)
        fun bind(position: Int, list: List<ExtendedIngredient>) {
            binding.tvNameIngr.text=list[position].name
            binding.tvQuantityIngr.text=list[position].original
            Glide.with(itemView.context)
                .load("https://spoonacular.com/cdn/ingredients_100x100/" + list[position].image)
                .into(binding.imViewIngr)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return IngredientAdapter.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_ingredient, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, list)
    }
}
