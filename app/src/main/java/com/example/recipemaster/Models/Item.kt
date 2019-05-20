package com.example.recipemaster.Models

data class Item(val name: String) {
    override fun toString(): String {
        return "$name"
    }
}