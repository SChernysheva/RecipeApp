package com.example.recipes.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipes.R
import com.example.recipes.data.database.DataBaseSaved
import com.example.recipes.data.models.DBModel
import com.example.recipes.data.models.Recipe
import com.example.recipes.databinding.RecipeItemBinding
import com.example.recipes.ui.fragments.BaseFragment

class RecipeAdapter(private val listener: BaseFragment): RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = RecipeItemBinding.bind(view)
        fun bind(dish: DBModel, listener: Listener) {
            Glide.with(itemView.context).load(dish.image).into(binding.imViewFood)
            binding.tvTitle.text=dish.title
            binding.tvServings.text=dish.servings.toString()
            binding.time.text=dish.readyInMinutes.toString()
            if (dish.isLike)binding.tvLike.setBackgroundColor(0xE80B0B.toInt())
            if (!dish.isLike) binding.tvLike.setBackgroundColor(0xFFFFFFFF.toInt())

            binding.tvLike.setOnClickListener {
                if (dish.isLike) binding.tvLike.setBackgroundColor(0xFFE80B0B.toInt())
                if (!dish.isLike) binding.tvLike.setBackgroundColor(0xE80B0B.toInt())
                listener.like(dish)
            }

            itemView.setOnClickListener { listener.openNew(dish.id) }
        }
    }

    private val differCallBack = object : DiffUtil.ItemCallback<DBModel>() {
        override fun areItemsTheSame(oldItem: DBModel, newItem: DBModel): Boolean {
            return oldItem.id==newItem.id
        }
        override fun areContentsTheSame(oldItem: DBModel, newItem: DBModel): Boolean {
            return oldItem==newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position], listener)

    }

    interface Listener{
        fun openNew(id:Int)
        fun like(dish:DBModel)
    }
}