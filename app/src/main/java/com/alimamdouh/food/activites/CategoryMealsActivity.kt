package com.alimamdouh.food.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.alimamdouh.food.R
import com.alimamdouh.food.adapter.MealsByCategoryAdapter
import com.alimamdouh.food.databinding.ActivityCategoryMealsBinding
import com.alimamdouh.food.fragments.HomeFragment
import com.alimamdouh.food.viewModel.MealsByCategoryViewModel

class CategoryMealsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryMealsBinding
    private lateinit var viewModel:MealsByCategoryViewModel
    private lateinit var mealsAdapter:MealsByCategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRv()
        viewModel=ViewModelProvider(this).get(MealsByCategoryViewModel::class.java)

        viewModel.getMealCategory(intent.getStringExtra(HomeFragment.CAT_NAME)!!)
        viewModel.observeMealCategoryLiveData().observe(this, Observer { mealList->
            var count=intent.getStringExtra(HomeFragment.CAT_NAME)+":"+mealList.size.toString()
            binding.categoryActTvCount.text=count
            mealsAdapter.setList(mealList)
        })
    }

    private fun prepareRv() {
        mealsAdapter= MealsByCategoryAdapter()
        binding.rvMeals.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=mealsAdapter
        }
    }
}