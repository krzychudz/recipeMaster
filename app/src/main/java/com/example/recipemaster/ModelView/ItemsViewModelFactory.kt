package com.example.recipemaster.ModelView

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.recipemaster.Models.ItemRepository

class ItemsViewModelFactory(private val itemRepository: ItemRepository)
    : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ItemsViewModel(itemRepository) as T
    }
}