package com.example.recipemaster

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.flexbox.FlexboxLayout
import com.squareup.picasso.Picasso


class RecipeAdapter(private val context: Context,
                    private val dataSource: ArrayList<Model>,
                    private val listener : SaveFileInterface
) : BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun getCount(): Int {
        return dataSource.size
    }


    override fun getItem(position: Int): Any {
        return dataSource[position]
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val rowView = inflater.inflate(R.layout.list_item_recipe, parent, false)

        val dishName = rowView.findViewById(R.id.name) as TextView
        val dishDescription = rowView.findViewById(R.id.description) as TextView
        val ingredientsList = rowView.findViewById(R.id.ingredients) as LinearLayout
        val stepsList = rowView.findViewById(R.id.preparing) as LinearLayout
        val imagesList = rowView.findViewById(R.id.flexboxImages) as FlexboxLayout

        val recipe = getItem(position) as Model

        dishName.text = recipe.title + ":"
        dishDescription.text = recipe.description


        for(i in recipe.ingredients){
            val singleIngredientView = TextView(context!!)

            singleIngredientView.text = "- " + i
            ingredientsList.addView(singleIngredientView)
        }

        for(i in 0..(recipe.preparing.size-1)){
            val singleStepView = TextView(context!!)
            singleStepView.setPadding(0,20,0,0)
            singleStepView.text = (i+1).toString() + ". " + recipe.preparing[i]
            stepsList.addView(singleStepView)
        }

        var imgIndex = 0

        for(i in recipe.imgs){
            val imageView = ImageView(context!!)
            Picasso.get().load(i).resize(600,400).into(imageView)

            imageView.setPadding(20,20,20,20)
            imageView.setTag("Image" + imgIndex)

            imagesList.addView(imageView)


            imageView.setOnClickListener{
                saveImage(imageView)
            }

            imgIndex++
        }



        return rowView
    }


    fun saveImage(iv: ImageView){
        val builder = AlertDialog.Builder(context!!)

        builder.setTitle("Save image")
        builder.setMessage("Do you want to save this image on your device?")

        builder.setPositiveButton("Yes"){dialog, which->
            listener.saveImageOnDevice(iv)
        }

        builder.setNegativeButton("No"){dialog, which->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }




}
