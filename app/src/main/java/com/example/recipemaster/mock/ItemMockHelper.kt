package com.example.recipemaster.mock

import com.example.recipemaster.Models.Item

class ItemMockHelper {

    val mockArticleData: List<Item>
        get() {
            val list = ArrayList<Item>()
            for (i in 0..24) {
                val article = Item()
                article.name = "Item"
                list.add(article)
            }
            return list
        }
}