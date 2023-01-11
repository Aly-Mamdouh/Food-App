package com.alimamdouh.food.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alimamdouh.food.databinding.PopularItemRvBinding
import com.alimamdouh.food.pojo.MealsByCategory
import com.bumptech.glide.Glide

class MostPopularAdapter():RecyclerView.Adapter<MostPopularAdapter.PopularViewHolder>(){
    lateinit var onItemCliked:((MealsByCategory)->Unit)
    var onItemLongClicked:((MealsByCategory)->Unit)?=null
    private var mealList=ArrayList<MealsByCategory>()

     fun setList(meal:ArrayList<MealsByCategory> ){
        this.mealList=meal
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(PopularItemRvBinding
                .inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.popularRvItemIv)

        holder.itemView.setOnClickListener {
            onItemCliked.invoke(mealList[position])
        }

        holder.itemView.setOnLongClickListener {
            onItemLongClicked?.invoke(mealList[position])
            true
        }
    }

    override fun getItemCount(): Int {
      return mealList.size
    }

    class PopularViewHolder(var binding: PopularItemRvBinding):RecyclerView.ViewHolder(binding.root){

    }

}