package com.alimamdouh.food.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alimamdouh.food.db.MealsDatabase

class HomeViewModelFactory(private val mealsDatabase: MealsDatabase):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(mealsDatabase) as T
    }
}