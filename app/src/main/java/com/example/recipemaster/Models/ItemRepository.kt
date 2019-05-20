package com.example.recipemaster.Models

class ItemRepository private constructor(private val itemDao: FakeItemsDao) {

    // This may seem redundant.
    // Imagine a code which also updates and checks the backend.
    fun addItem(item: Item) {
        itemDao.addItem(item)
    }

    fun getItems() = itemDao.getItems()

    companion object {
        // Singleton instantiation you already know and love
        @Volatile private var instance: ItemRepository? = null

        fun getInstance(itemDao: FakeItemsDao) =
            instance ?: synchronized(this) {
                instance ?: ItemRepository(itemDao).also { instance = it }
            }
    }
}