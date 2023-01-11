package com.alimamdouh.food.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alimamdouh.food.db.MealsDatabase
import com.alimamdouh.food.pojo.*
import com.alimamdouh.food.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val mealsDatabase: MealsDatabase) : ViewModel() {
    private var mealLiveData = MutableLiveData<Meal>()
    private var popularLiveData = MutableLiveData<List<MealsByCategory>>()
    private var categoriesLiveData = MutableLiveData<List<Category>>()
    private var bottomSheetLiveData = MutableLiveData<Meal>()
    private var favLivData=mealsDatabase.mealDao().getAllMeals()
    private var saveRandomMeal:Meal?=null


    fun getRandomMeal() {
        saveRandomMeal?.let {randomMeal->
            mealLiveData.postValue(randomMeal)
            return
        }
        RetrofitInstance.api.getRandomMeals().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    val meal = response.body()!!.meals[0]
                    mealLiveData.value = meal
                    saveRandomMeal=meal
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("TEST", t.message.toString())
            }

        })
    }

    fun getPopularItem() {
        RetrofitInstance.api.getPopularItems("Seafood").enqueue(object : Callback<MealsByCategoryList> {
            override fun onResponse(call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>) {
                if (response.body() != null) {
                    val meals = response.body()!!.meals
                    popularLiveData.value = meals
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.d("TEST", t.message.toString())
            }

        })

    }

    fun getCategory(){
        RetrofitInstance.api.getCategoriesItems().enqueue(object :Callback<CategoriesList>{
            override fun onResponse(
                call: Call<CategoriesList>,
                response: Response<CategoriesList>
            ) {
                response.body()?.let { catList->
                    categoriesLiveData.postValue(catList.categories)
                }
            }

            override fun onFailure(call: Call<CategoriesList>, t: Throwable) {
                Log.d("TEST", t.message.toString())
            }

        })
    }
    fun getMealByID(id:String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object :Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val meal=response.body()?.meals?.first()
                meal?.let {
                    meals->
                    bottomSheetLiveData.postValue(meals)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("TEST", t.message.toString())
            }

        })
    }
    fun observeRandomMealLiveData(): LiveData<Meal> {
        return mealLiveData
    }

    fun observeBottomSheetLiveData(): LiveData<Meal> {
        return bottomSheetLiveData
    }

    fun observePopularItemsLiveData(): LiveData<List<MealsByCategory>> {
        return popularLiveData
    }

    fun observeCategoriesItemsLiveData(): LiveData<List<Category>> {
        return categoriesLiveData
    }

    fun observeFavLiveData():LiveData<List<Meal>>{
        return favLivData
    }

    fun deleteMeal(meal: Meal){
        viewModelScope.launch {
            mealsDatabase.mealDao().deleteMeal(meal)
        }
    }
    fun insertMeal(meal:Meal){
        viewModelScope.launch {
            mealsDatabase.mealDao().upsert(meal)
        }
    }
}