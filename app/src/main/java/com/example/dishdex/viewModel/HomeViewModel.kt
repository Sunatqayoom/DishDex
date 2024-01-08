package com.example.dishdex.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Database
import androidx.room.Query
import com.example.dishdex.db.MealDatabase
import com.example.dishdex.pojo.Category
import com.example.dishdex.pojo.CategoryList
import com.example.dishdex.pojo.MealsByCategoryList
import com.example.dishdex.pojo.MealsByCategory
import com.example.dishdex.pojo.Meal
import com.example.dishdex.pojo.mealList
import com.example.dishdex.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val mealDatabase:MealDatabase): ViewModel() {

    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData = MutableLiveData<List<MealsByCategory>>()
    private var categoriesLiveData=MutableLiveData<List<Category>>()
    private var favouriteMealsLiveData=mealDatabase.mealDao().getAllMeals()
    private var searchmealsLiveData=MutableLiveData<List<Meal>>()

    fun getRandomMeal() { // Corrected the function name
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<mealList> {
            override fun onResponse(call: Call<mealList>, response: Response<mealList>) {
                if (response.body() != null) {
                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                } else {
                    // Handle the case when response.body() is null
                    return
                }
            }

            override fun onFailure(call: Call<mealList>, t: Throwable) {
                // Handle failure
            }
        })
    }

    fun getPopularItems() {
        RetrofitInstance.api.getPopularItem("Seafood").enqueue(object : Callback<MealsByCategoryList> {
            override fun onResponse(call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>) {
                if (response.body() != null) {
                    popularItemsLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                // Handle failure
            }
        })
    }
    fun getCategories() {
        RetrofitInstance.api.getCategories().enqueue(object :Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {

               response.body()?.let {
                   categoryList ->
                   categoriesLiveData.postValue(categoryList.categories)
               }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }


    fun observeSearchMealsLiveData() :LiveData<List<Meal>> = searchmealsLiveData
    fun observeRandomMealLiveData(): LiveData<Meal> {
        return randomMealLiveData
    }

    fun observePopularItemsLiveData(): LiveData<List<MealsByCategory>> {
        return popularItemsLiveData
    }
    fun observeCategoriesLiveData() : LiveData<List<Category>> {
        return categoriesLiveData
    }
    fun observeFavouritesmealsLiveData() :LiveData<List<Meal>> {
        return favouriteMealsLiveData
    }

    fun searchMeals(searchQuery: String)=RetrofitInstance.api.searchMeals(searchQuery).enqueue(object : Callback<mealList>{
        override fun onResponse(call: Call<mealList>, response: Response<mealList>) {
            val mealsList=response.body()?.meals
            mealsList?.let { searchmealsLiveData.postValue(it) }

        }

        override fun onFailure(call: Call<mealList>, t: Throwable) {
            TODO("Not yet implemented")
        }

    }

    )
}



