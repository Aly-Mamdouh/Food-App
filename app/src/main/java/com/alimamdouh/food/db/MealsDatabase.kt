package com.alimamdouh.food.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alimamdouh.food.pojo.Meal

@Database(entities = [Meal::class], version = 1)
@TypeConverters(MealConverter::class)
abstract class MealsDatabase :RoomDatabase(){
    abstract fun mealDao():MealsDao

    companion object{
        @Volatile // any change in this instance from any thread will be visible from any thread
        var instance:MealsDatabase?=null

        @Synchronized // only one thread have instance from database
        fun getInstance(context:Context):MealsDatabase{
            if(instance==null){
                instance=Room.databaseBuilder(context.applicationContext
                ,MealsDatabase::class.java,"meal.db")
                    .fallbackToDestructiveMigration() // keep data inside database
                    .build()
            }
            return instance as MealsDatabase
        }

    }
}