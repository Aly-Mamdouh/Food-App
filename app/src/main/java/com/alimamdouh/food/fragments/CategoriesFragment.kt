package com.alimamdouh.food.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.alimamdouh.food.R
import com.alimamdouh.food.activites.MainActivity
import com.alimamdouh.food.adapter.CategoriesAdapter
import com.alimamdouh.food.databinding.FragmentCategoriesBinding
import com.alimamdouh.food.viewModel.HomeViewModel

class CategoriesFragment : Fragment() {
  private lateinit var binding:FragmentCategoriesBinding
  private lateinit var viewModel:HomeViewModel
  private lateinit var catAdapter:CategoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=(activity as MainActivity).viewModel
        catAdapter= CategoriesAdapter()

    }

  override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentCategoriesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecView()
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.observeCategoriesItemsLiveData().observe(viewLifecycleOwner, Observer {
            meals->
            catAdapter.setList(meals)
        })
    }

    private fun prepareRecView() {
        binding.rvCategories.apply {
            layoutManager=GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter=catAdapter
        }
    }

}