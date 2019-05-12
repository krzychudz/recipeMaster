package com.example.recipemaster


import java.io.Serializable


class Model (
    val title : String,
    val description : String,
    val ingredients : Array<String>,
    val preparing : Array<String>,
    val imgs : Array<String>
) : Serializable

