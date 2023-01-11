package com.alimamdouh.food.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alimamdouh.food.databinding.CategoriesItemsRvBinding
import com.alimamdouh.food.databinding.CategoryShowItemRvBinding
import com.alimamdouh.food.pojo.Meal
import com.bumptech.glide.Glide

class FavoritesAdapter():RecyclerView.Adapter<FavoritesAdapter.FavViewHolder>() {

    private val diffUtil=object :DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal==newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem==newItem
        }

    }

    val differ=AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        return FavViewHolder(
            CategoryShowItemRvBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        val meal=differ.currentList[position]
        Glide.with(holder.itemView)
            .load(meal.strMealThumb)
            .into(holder.binding.catIv)

        holder.binding.catTvName.text=meal.strMeal
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class FavViewHolder(val binding: CategoryShowItemRvBinding):RecyclerView.ViewHolder(binding.root) {
    }
}