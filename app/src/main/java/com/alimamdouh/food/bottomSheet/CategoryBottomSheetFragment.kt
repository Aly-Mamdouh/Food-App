package com.alimamdouh.food.bottomSheet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.alimamdouh.food.R
import com.alimamdouh.food.activites.MainActivity
import com.alimamdouh.food.activites.MealActivity
import com.alimamdouh.food.databinding.FragmentCategoryBottomSheetBinding
import com.alimamdouh.food.fragments.HomeFragment
import com.alimamdouh.food.viewModel.HomeViewModel
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private const val MEAL_ID = "meal_id"

class CategoryBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentCategoryBottomSheetBinding
    private var mealID: String? = null
    private lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        arguments?.let {
            mealID = it.getString(MEAL_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mealID?.let { meal ->
            viewModel.getMealByID(meal)
        }
        observeBottomSheetLiveData()
        onBottomSheetDialogClicked()
    }

    private fun onBottomSheetDialogClicked() {
        binding.bottomSheet.setOnClickListener {
            if (mealName != null && mealThumb != null) {
                val intent = Intent(activity, MealActivity::class.java)
                intent.apply {
                    putExtra(HomeFragment.MEAL_ID, mealID)
                    putExtra(HomeFragment.MEAL_NAME, mealName)
                    putExtra(HomeFragment.MEAL_THUMB, mealThumb)
                }
                startActivity(intent)
            }
        }
    }

    private var mealName: String? = null
    private var mealThumb: String? = null
    private fun observeBottomSheetLiveData() {
        viewModel.observeBottomSheetLiveData().observe(viewLifecycleOwner, Observer { meal ->
            Glide.with(this)
                .load(meal.strMealThumb).into(binding.bottomSheetIv)

            binding.bottomSheetTvMealName.text = meal.strMeal
            binding.bottomSheetTvCat.text = meal.strCategory
            binding.bottomSheetTvArea.text = meal.strArea
            mealName = meal.strMeal
            mealThumb = meal.strMealThumb
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(mealId: String) =
            CategoryBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, mealId)
                }
            }
    }
}