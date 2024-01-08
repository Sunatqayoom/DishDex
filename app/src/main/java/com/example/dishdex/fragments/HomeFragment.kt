package com.example.dishdex.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.dishdex.R
import com.example.dishdex.activities.CategoryMealsActivity
import com.example.dishdex.activities.MainActivity
import com.example.dishdex.activities.MealActivity
import com.example.dishdex.adapters.CategoriesAdapter
import com.example.dishdex.adapters.MostPopularAdapter
import com.example.dishdex.databinding.FragmentHomeBinding
import com.example.dishdex.pojo.Meal
import com.example.dishdex.pojo.MealsByCategory
import com.example.dishdex.viewModel.HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemAdapter: MostPopularAdapter
    private lateinit var categoriesAdapter :CategoriesAdapter

    companion object {
        const val MEAL_ID = "com.example.dishdex.fragments.idMeal"
        const val MEAL_NAME = "com.example.dishdex.fragments.nameMeal"
        const val MEAL_THUMB = "com.example.dishdex.fragments.thumbMeal"
        const val CATEGORY_NAME="com.example.dishdex.fragments.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel=(activity as MainActivity).viewModel
        popularItemAdapter = MostPopularAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preparePopularItemsRecyclerView()
        viewModel.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()
        viewModel.getPopularItems()
        observerPopularItemsLiveData()
        onPopularItemClick()

        onSearchItemClick()
        prepareCategoriesRecyclerView()
        viewModel.getCategories()
        observerCategoriesLiveData()
        onCategoryClick()
    }

    private fun onSearchItemClick() {
        binding.imgSearch.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_seearchFragment)
        }
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick={category ->
            val intent=Intent(activity,CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)
            
        }

    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter=CategoriesAdapter()
        binding.recyclerView.apply {
            layoutManager=GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter=categoriesAdapter
        }
    }

    private fun observerCategoriesLiveData() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer { categories->
                categoriesAdapter.setCategoryList(categories)

        })
    }

    private fun onPopularItemClick() {
        popularItemAdapter.onItemClick={ meal -> val intent=Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)

        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemAdapter
        }
    }

    private fun observerPopularItemsLiveData() {
        viewModel.observePopularItemsLiveData().observe(viewLifecycleOwner, Observer { value ->
            popularItemAdapter.setMeals(value as ArrayList<MealsByCategory>)
        })
    }

    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {
        viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner, Observer { value ->
            randomMeal = value
            Glide.with(requireContext()).load(value.strMealThumb).into(binding.imgRandomMeal)
        })
    }
}
