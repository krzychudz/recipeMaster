package com.example.recipemaster.ModelView

import android.arch.lifecycle.ViewModel
import com.example.recipemaster.Models.Item
import com.example.recipemaster.Models.ItemRepository

class ItemsViewModel(private val itemsRepository: ItemRepository)
    : ViewModel() {

    fun getItems() = itemsRepository.getItems()

    fun addItem(item: Item) = itemsRepository.addItem(item)
}