package com.alimamdouh.food.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alimamdouh.food.databinding.CategoriesItemsRvBinding
import com.alimamdouh.food.pojo.Category
import com.bumptech.glide.Glide

class CategoriesAdapter():RecyclerView.Adapter<CategoriesAdapter.CVH>() {
    private var categoryList=ArrayList<Category>()
    lateinit var onItemCliked:((Category)->Unit)
    fun setList(cat:List<Category> ){
        this.categoryList=cat as ArrayList<Category>
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CVH {
       return CategoriesAdapter.CVH(CategoriesItemsRvBinding.inflate(LayoutInflater
           .from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CVH, position: Int) {
        Glide.with(holder.itemView)
            .load(categoryList[position].strCategoryThumb)
            .into(holder.binding.categoryRvIv)

         holder.binding.categoryRvTvName.text=categoryList[position].strCategory

        holder.itemView.setOnClickListener {
            onItemCliked.invoke(categoryList[position])
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    class CVH(val binding: CategoriesItemsRvBinding):RecyclerView.ViewHolder(binding.root)
}