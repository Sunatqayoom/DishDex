package com.example.dishdex.retrofit

import com.example.dishdex.pojo.CategoryList
import com.example.dishdex.pojo.MealsByCategoryList
import com.example.dishdex.pojo.mealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal() : Call<mealList>

    @GET("lookup.php?")
    fun getMealDetails(@Query("i")id:String):Call<mealList>

    @GET("filter.php?")
    fun getPopularItem(@Query("c") categoryName: String) :Call<MealsByCategoryList>

    @GET("categories.php")
    fun getCategories():Call<CategoryList>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") categoryName: String):Call<MealsByCategoryList>

    @GET("search.php")
    fun searchMeals(@Query("s") searchQuery: String):Call<mealList>

}