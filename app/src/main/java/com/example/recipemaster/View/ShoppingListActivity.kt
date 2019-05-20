package com.example.recipemaster.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.recipemaster.ModelView.ItemsViewModel
import com.example.recipemaster.Models.Item
import com.example.recipemaster.R
import com.example.recipemaster.Utillities.InjectorUtils
import kotlinx.android.synthetic.main.content_shopping_list.*

class ShoppingListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_list)


        initializeUi()
    }

    private fun initializeUi() {
        // Get the QuotesViewModelFactory with all of it's dependencies constructed
        val factory = InjectorUtils.provideItemsViewModelFactory()
        // Use ViewModelProviders class to create / get already created QuotesViewModel
        // for this view (activity)
        val viewModel = ViewModelProviders.of(this, factory)
            .get(ItemsViewModel::class.java)


        // Observing LiveData from the QuotesViewModel which in turn observes
        // LiveData from the repository, which observes LiveData from the DAO ☺
        viewModel.getItems().observe(this, Observer { items ->
            val stringBuilder = StringBuilder()
            items.forEach { item ->
                stringBuilder.append("$item\n\n")
            }
            items_textView.text = stringBuilder.toString()
        })

        // When button is clicked, instantiate a Quote and add it to DB through the ViewModel
        add_button.setOnClickListener {
            val quote = Item(new_item.text.toString())
            viewModel.addQuote(quote)
            new_item.setText("")

        }
    }

}
