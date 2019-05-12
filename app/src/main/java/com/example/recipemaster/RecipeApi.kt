package com.example.recipemaster
import retrofit2.Call
import retrofit2.http.GET

interface RecipeApi {

    @GET("info.php")
    fun getData() : Call<Model>
}