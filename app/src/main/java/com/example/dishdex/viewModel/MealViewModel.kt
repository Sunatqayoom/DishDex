package com.example.dishdex.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dishdex.db.MealDatabase
import com.example.dishdex.pojo.Meal
import com.example.dishdex.pojo.mealList
import com.example.dishdex.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel( val mealDatabase: MealDatabase) : ViewModel() {
    private var mealDetailsLiveData = MutableLiveData<Meal>()

    fun getMealDetail(id: String) {
        RetrofitInstance.api.getMealDetails(id).enqueue(object : Callback<mealList> {
            override fun onResponse(call: Call<mealList>, response: Response<mealList>) {
                if (response.body() != null) {
                    mealDetailsLiveData.value = response.body()!!.meals[0]
                } else {
                    // Handle the case where response body is null
                }
            }

            override fun onFailure(call: Call<mealList>, t: Throwable) {
                // Handle the failure case
            }
        })
    }

    fun observeMealDetailsLiveData(): LiveData<Meal> {
        return mealDetailsLiveData
    }
    fun insertMeal(meal: Meal) {
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }
    fun deleteMeal(meal: Meal) {
        viewModelScope.launch { mealDatabase.mealDao().delete(meal) }
    }
}
