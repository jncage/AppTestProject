package com.example.apptestproject.ui

import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.apptestproject.R
import com.example.apptestproject.models.Dish
import com.squareup.picasso.Picasso

class AddToCartDialog(
    private val picasso: Picasso,
    private val context: Context,
    private val dish: Dish,
    private val addToCartListener: () -> Unit
) {

    fun show() {
        val popupView = LayoutInflater.from(context).inflate(R.layout.popup_add_to_cart, null)
        val builder = AlertDialog.Builder(context)

        val dishName = popupView.findViewById<TextView>(R.id.dishNameView)
        val dishImage = popupView.findViewById<ImageView>(R.id.dishImagePopUp)
        val dishPrice = popupView.findViewById<TextView>(R.id.priceView)
        dishPrice.text = context.getString(R.string.price, dish.price)
        val dishWeight = popupView.findViewById<TextView>(R.id.weightView)
        dishWeight.text = context.getString(R.string.weight, dish.weight)
        val dishDescription = popupView.findViewById<TextView>(R.id.descriptionView)
        dishDescription.text = dish.description
        picasso.load(dish.imageUrl).into(dishImage)
        dishName.text = dish.name
        builder.setView(popupView)
        builder.setCancelable(false)
        val alertDialog = builder.create()
        alertDialog.window?.setBackgroundDrawableResource(R.drawable.cornered_shape_15dp)
        alertDialog.show()

        val addToCartButton = popupView.findViewById<TextView>(R.id.addToCartButton)
        addToCartButton.setOnClickListener {
            addToCartListener.invoke()
            alertDialog.dismiss()
        }

        val dismissButton = popupView.findViewById<ImageView>(R.id.closeButton)
        dismissButton.setOnClickListener {
            alertDialog.dismiss()
        }
    }
}
