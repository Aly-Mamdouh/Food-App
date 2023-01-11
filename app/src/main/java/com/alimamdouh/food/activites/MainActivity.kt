package com.alimamdouh.food.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.alimamdouh.food.R
import com.alimamdouh.food.databinding.ActivityMainBinding
import com.alimamdouh.food.db.MealsDatabase
import com.alimamdouh.food.viewModel.HomeViewModel
import com.alimamdouh.food.viewModel.HomeViewModelFactory

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val viewModel:HomeViewModel by lazy {
        val mealDatabase=MealsDatabase.getInstance(this)
        val homeViewModelFactory=HomeViewModelFactory(mealDatabase)
        ViewModelProvider(this,homeViewModelFactory)[HomeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView= binding.btmNav
        val navController=Navigation.findNavController(this, R.id.host_fragment)
        NavigationUI.setupWithNavController(bottomNavigationView,navController)


    }
}