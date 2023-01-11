package com.alimamdouh.food.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alimamdouh.food.db.MealsDatabase
import com.alimamdouh.food.pojo.Meal
import com.alimamdouh.food.pojo.MealList
import com.alimamdouh.food.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealDetailsViewModel(
    val mealsDatabase: MealsDatabase
):ViewModel() {
    private var mealDetailsLiveData = MutableLiveData<Meal>()

    fun getMealDetails(id:String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object :Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                mealDetailsLiveData.value= response.body()!!.meals[0]
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("mealDetails",t.message.toString())
            }
        })
    }
    fun observeDetailsMealLiveData(): LiveData<Meal> {
        return mealDetailsLiveData
    }

    fun insertMeal(meal:Meal){
        viewModelScope.launch {
            mealsDatabase.mealDao().upsert(meal)
        }
    }

}