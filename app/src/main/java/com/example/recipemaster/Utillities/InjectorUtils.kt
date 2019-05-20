package com.example.recipemaster.Utillities

import com.example.recipemaster.ModelView.ItemsViewModelFactory
import com.example.recipemaster.Models.FakeDatabase
import com.example.recipemaster.Models.ItemRepository

object InjectorUtils {

    // This will be called from QuotesActivity
    fun provideItemsViewModelFactory(): ItemsViewModelFactory {
        // ViewModelFactory needs a repository, which in turn needs a DAO from a database
        // The whole dependency tree is constructed right here, in one place
        val quoteRepository = ItemRepository.getInstance(FakeDatabase.getInstance().quoteDao)
        return ItemsViewModelFactory(quoteRepository)
    }
}