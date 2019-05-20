package com.example.recipemaster.View

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.recipemaster.Models.Item
import com.example.recipemaster.R

class ItemsAdapter(private val mItems: List<Item>) : RecyclerView.Adapter<ItemsAdapter.BindingHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder {
        val binding = DataBindingUtil.inflate<RowArticleBinding>(
            LayoutInflater.from(parent.context),
            R.layout.row_article, parent, false)

        return BindingHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
        val binding = holder.binding
        binding.avm = ArticleViewModel(mArticles[position])
    }

    override fun getItemCount(): Int {
        return mArticles.size
    }

    class BindingHolder(val binding: RowArticleBinding) : RecyclerView.ViewHolder(binding.mainLayout)
}