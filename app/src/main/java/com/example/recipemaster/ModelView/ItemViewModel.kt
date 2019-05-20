package com.example.recipemaster.ModelView

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.example.recipemaster.Models.Item


class ItemViewModel(private val mItem: Item) : BaseObservable(){

    var title: String?
    @Bindable
    get() = mItem.name
    set(name){
        mItem.name = name
        notifyPropertyChanged(BR.name)
    }




}