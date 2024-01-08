package com.example.dishdex.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dishdex.pojo.MealsByCategory
import com.example.dishdex.pojo.MealsByCategoryList
import com.example.dishdex.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel : ViewModel() {
    val mealsLiveData = MutableLiveData<List<MealsByCategory>>()

    fun getMealsByCategory(categoryName: String) {
        RetrofitInstance.api.getMealsByCategory(categoryName)
            .enqueue(object : Callback<MealsByCategoryList> {
                override fun onResponse(
                    call: Call<MealsByCategoryList>,
                    response: Response<MealsByCategoryList>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { mealsList ->
                            mealsLiveData.postValue(mealsList.meals)
                        }
                    }
                }

                override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                    // Handle failure here
                }
            })
    }
    fun observeMealsLiveData(): LiveData<List<MealsByCategory>> {
        return mealsLiveData

    }
}
