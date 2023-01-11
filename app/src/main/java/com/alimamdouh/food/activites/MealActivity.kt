package com.alimamdouh.food.activites

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alimamdouh.food.R
import com.alimamdouh.food.databinding.ActivityMealBinding
import com.alimamdouh.food.db.MealsDatabase
import com.alimamdouh.food.fragments.HomeFragment
import com.alimamdouh.food.pojo.Meal
import com.alimamdouh.food.viewModel.MealDetailsViewModel
import com.alimamdouh.food.viewModel.MealDetailsViewModelFactory
import com.bumptech.glide.Glide

class MealActivity : AppCompatActivity() {
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var youtubeLink: String
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealViewModel: MealDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mealDb = MealsDatabase.getInstance(this)
        val viewModelFactory = MealDetailsViewModelFactory(mealDb)

        mealViewModel = ViewModelProvider(this, viewModelFactory)[MealDetailsViewModel::class.java]

        getMealInfoFromIntent()
        setInfoToViews()
        loadingCase()
        mealViewModel.getMealDetails(mealId)
        observeMealDetailsLiveData()
        onClickOnYoutubeImage()
        onFavClicked()
    }

       private fun onFavClicked() {
           binding.btnSave.setOnClickListener {
               mealToSave?.let {
                   mealViewModel.insertMeal(it)
                   Toast.makeText(this,"Meal Saved",Toast.LENGTH_LONG).show()
               }
           }
       }

    private fun onClickOnYoutubeImage() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private var mealToSave: Meal? = null

    private fun observeMealDetailsLiveData() {
        mealViewModel.observeDetailsMealLiveData().observe(this, object : Observer<Meal> {
            override fun onChanged(t: Meal?) {
                onResponseCase()
                val meal = t
                mealToSave = meal

                binding.tvCategoryInfo.text = "Category : ${meal!!.strCategory}"
                binding.tvAreaInfo.text = "Area : ${meal.strArea}"
                binding.tvContent.text = meal.strInstructions
                youtubeLink = meal.strYoutube.toString()
            }

        })
    }


    private fun setInfoToViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))

    }

    private fun getMealInfoFromIntent() {
        val intent = intent

        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun loadingCase() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnSave.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvCategoryInfo.visibility = View.INVISIBLE
        binding.tvAreaInfo.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
    }

    private fun onResponseCase() {
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnSave.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvCategoryInfo.visibility = View.VISIBLE
        binding.tvAreaInfo.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE

    }
}