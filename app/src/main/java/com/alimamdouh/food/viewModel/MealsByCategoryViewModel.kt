package com.alimamdouh.food.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alimamdouh.food.pojo.MealsByCategory
import com.alimamdouh.food.pojo.MealsByCategoryList
import com.alimamdouh.food.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealsByCategoryViewModel():ViewModel() {
     val mealCategoryLiveData = MutableLiveData<List<MealsByCategory>>()

    fun getMealCategory(catName:String){
        RetrofitInstance.api.getMealsByCategory(catName).enqueue(object :Callback<MealsByCategoryList>{
            override fun onResponse(
                call: Call<MealsByCategoryList>,
                response: Response<MealsByCategoryList>
            ) {
                response.body()?.let { cats->
                    mealCategoryLiveData.postValue(cats.meals)
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.d("mealByCategory",t.message.toString())
            }

        })
    }

    fun observeMealCategoryLiveData(): LiveData<List<MealsByCategory>> {
        return mealCategoryLiveData
    }
}