package com.alimamdouh.food.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alimamdouh.food.pojo.Meal

@Dao
interface MealsDao {

    //for insert and update
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(meal:Meal)

    @Delete
    suspend fun deleteMeal(meal:Meal)

    @Query("select * from mealInformation")
    fun getAllMeals():LiveData<List<Meal>>
}