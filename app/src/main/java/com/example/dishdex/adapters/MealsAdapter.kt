package com.example.dishdex.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.dishdex.databinding.MealItemBinding
import com.example.dishdex.pojo.Meal

class MealsAdapter:RecyclerView.Adapter<MealsAdapter.favoritesMealsAdapterViewHolder>() {
    inner class favoritesMealsAdapterViewHolder( val binding: MealItemBinding):ViewHolder(binding.root)
    private val diffUtil=object:DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal==newItem.idMeal

        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem==newItem
        }

    }
    val differ=AsyncListDiffer(this,diffUtil)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): favoritesMealsAdapterViewHolder {
        return favoritesMealsAdapterViewHolder(MealItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: favoritesMealsAdapterViewHolder, position: Int) {

        val meal=differ.currentList[position]
        Glide.with(holder.itemView).load(meal.strMealThumb).into(holder.binding.imgMeal)
        holder.binding.tvMealName.text=meal.strMeal
    }
}