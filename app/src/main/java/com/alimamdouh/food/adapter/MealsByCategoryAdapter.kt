package com.alimamdouh.food.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alimamdouh.food.databinding.CategoryShowItemRvBinding
import com.alimamdouh.food.pojo.Category
import com.alimamdouh.food.pojo.MealsByCategory
import com.bumptech.glide.Glide
import kotlinx.coroutines.joinAll

class MealsByCategoryAdapter():RecyclerView.Adapter<MealsByCategoryAdapter.MBCVH>(){
    private var mealsList=ArrayList<MealsByCategory>()

    fun setList(meals:List<MealsByCategory> ){
        this.mealsList=meals as ArrayList<MealsByCategory>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MBCVH {
        return MealsByCategoryAdapter.MBCVH(CategoryShowItemRvBinding.inflate(LayoutInflater
            .from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MBCVH, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.catIv)

        holder.binding.catTvName.text=mealsList[position].strMeal
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }


    class MBCVH(var binding: CategoryShowItemRvBinding):RecyclerView.ViewHolder(binding.root)

}