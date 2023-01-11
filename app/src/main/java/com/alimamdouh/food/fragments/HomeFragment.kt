package com.alimamdouh.food.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.alimamdouh.food.activites.CategoryMealsActivity
import com.alimamdouh.food.activites.MainActivity
import com.alimamdouh.food.activites.MealActivity
import com.alimamdouh.food.adapter.CategoriesAdapter
import com.alimamdouh.food.adapter.MostPopularAdapter
import com.alimamdouh.food.bottomSheet.CategoryBottomSheetFragment
import com.alimamdouh.food.databinding.FragmentHomeBinding
import com.alimamdouh.food.pojo.MealsByCategory
import com.alimamdouh.food.pojo.Meal
import com.alimamdouh.food.viewModel.HomeViewModel
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemAdapter: MostPopularAdapter
    private lateinit var CategoriesAdapter: CategoriesAdapter

    companion object {
        const val MEAL_ID = "com.alimamdouh.food.fragments.mealId"
        const val MEAL_NAME = "com.alimamdouh.food.fragments.mealName"
        const val MEAL_THUMB = "com.alimamdouh.food.fragments.mealThumb"
        const val CAT_NAME = "com.alimamdouh.food.fragments.catName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=(activity as MainActivity).viewModel

        popularItemAdapter = MostPopularAdapter()
        CategoriesAdapter = CategoriesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        prepareMostItemRecView()
        viewModel.getRandomMeal()
        observeRandomMeal()
        onRandomMealClick()
        viewModel.getPopularItem()
        observePopularItemLiveData()
        onPopularItemClicked()

        viewModel.getCategory()
        observeCategoriesItemLiveData()
        prepareCategoryItemRecView()
        onCategoryClick()
        onLongItemClick()
    }

    private fun onLongItemClick() {
        popularItemAdapter.onItemLongClicked={meal->
            val bottomSheetFragment=CategoryBottomSheetFragment.newInstance(meal.idMeal)
            bottomSheetFragment.show(childFragmentManager,"Meal Info")
        }
    }

    private fun onCategoryClick() {
        CategoriesAdapter.onItemCliked={ category ->
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(CAT_NAME, category.strCategory)
            startActivity(intent)
        }
    }

    private fun onPopularItemClicked() {
        popularItemAdapter.onItemCliked = { meal ->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun prepareMostItemRecView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemAdapter
        }
    }
    private fun prepareCategoryItemRecView() {
        binding.recyclerViewCategories.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter = CategoriesAdapter
        }
    }

    private fun observePopularItemLiveData() {
        viewModel.observePopularItemsLiveData().observe(
            viewLifecycleOwner
        ) { mealList ->
            popularItemAdapter.setList(mealList as ArrayList<MealsByCategory>)
        }
    }
    private fun observeCategoriesItemLiveData() {
        viewModel.observeCategoriesItemsLiveData().observe(
            viewLifecycleOwner
        ) { cats ->
            CategoriesAdapter.setList(cats)
        }
    }
    private fun onRandomMealClick() {
        binding.imgRandomMeal.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observeRandomMeal() {
        viewModel.observeRandomMealLiveData().observe(
            viewLifecycleOwner
        ) { t ->
            Glide.with(this@HomeFragment)
                .load(t?.strMealThumb)
                .into(binding.imgRandomMeal)
            this.randomMeal = t
        }
    }

}