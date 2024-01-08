package com.example.dishdex.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dishdex.adapters.CategoryMealsAdapter
import com.example.dishdex.databinding.ActivityCategoryMealsBinding
import com.example.dishdex.fragments.HomeFragment
import com.example.dishdex.viewModel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategoryMealsBinding
    lateinit var categoryMealsViewModel: CategoryMealsViewModel
    lateinit var categoryMealsAdapter: CategoryMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerView()

        categoryMealsViewModel = ViewModelProvider(this).get(CategoryMealsViewModel::class.java)

        // Retrieve the category name from the intent
        val categoryName = intent.getStringExtra(HomeFragment.CATEGORY_NAME)
        if (categoryName != null) {
            categoryMealsViewModel.getMealsByCategory(categoryName)
        }

        // Observe the mealsLiveData
        categoryMealsViewModel.mealsLiveData.observe(this, Observer { mealsList ->
            // Handle the observed data (mealsList) here
            binding.tvCategoryCount.text=mealsList.size.toString()
            categoryMealsAdapter.setMealsList(mealsList)
        })
    }

    private fun prepareRecyclerView() {
        categoryMealsAdapter= CategoryMealsAdapter()
        binding.rvMeals.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=categoryMealsAdapter
        }

    }
}
