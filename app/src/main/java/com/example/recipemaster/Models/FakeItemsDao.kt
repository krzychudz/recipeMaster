package com.example.recipemaster.Models

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData


class FakeItemsDao {
    // A fake database table
    private val itemsList = mutableListOf<Item>()
    // MutableLiveData is from the Architecture Components Library
    // LiveData can be observed for changes
    private val items = MutableLiveData<List<Item>>()

    init {
        // Immediately connect the now empty quoteList
        // to the MutableLiveData which can be observed
        items.value = itemsList
    }

    fun addItem(item: Item) {
        itemsList.add(item)
        // After adding a quote to the "database",
        // update the value of MutableLiveData
        // which will notify its active observers
        items.value = itemsList
    }

    // Casting MutableLiveData to LiveData because its value
    // shouldn't be changed from other classes
    fun getItems() = items as LiveData<List<Item>>
}