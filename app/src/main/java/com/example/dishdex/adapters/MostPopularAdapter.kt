package com.example.dishdex.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dishdex.databinding.PopularItemBinding
import com.example.dishdex.pojo.MealsByCategory

class MostPopularAdapter : RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {
    private var mealsList = ArrayList<MealsByCategory>()
    lateinit var onItemClick:((MealsByCategory) -> Unit)
    fun setMeals(mealsList: ArrayList<MealsByCategory>) {
        // Update the adapter's data and notify the change
        this.mealsList = mealsList
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(
            PopularItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        // Return the size of the mealsList
        return mealsList.size
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        // Use Glide to load the image into the ImageView in the layout
        Glide.with(holder.itemView).load(mealsList[position].strMealThumb)
            .into(holder.binding.imgPopularMealItem)
        holder.itemView.setOnClickListener{
            onItemClick.invoke(mealsList[position])
        }
    }
    class PopularMealViewHolder(val binding: PopularItemBinding) : RecyclerView.ViewHolder(binding.root)
}
