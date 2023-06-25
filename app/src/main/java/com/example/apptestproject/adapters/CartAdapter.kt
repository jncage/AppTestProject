package com.example.apptestproject.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apptestproject.R
import com.example.apptestproject.models.CartItem
import com.squareup.picasso.Picasso
import javax.inject.Inject

class CartAdapter(
    private var cartItems: List<CartItem>,
    private val onPlusClick: (CartItem) -> Unit,
    private val onMinusClick: (CartItem) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    @Inject
    lateinit var picasso: Picasso
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(itemView, parent.context)
    }

    override fun getItemCount() = cartItems.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartItems[position]
        holder.bind(cartItem)
    }
    fun updateCartItems(items: List<CartItem>) {
        this.cartItems = items
        notifyDataSetChanged()
    }
    inner class CartViewHolder(itemView: View, private val context: Context) : RecyclerView.ViewHolder(itemView) {
        private val dishImage = itemView.findViewById<ImageView>(R.id.dishImageCart)
        private val dishName = itemView.findViewById<TextView>(R.id.dishNameCart)
        private val dishPrice = itemView.findViewById<TextView>(R.id.priceView)
        private val dishWeight = itemView.findViewById<TextView>(R.id.weightView)
        private val dishAmount = itemView.findViewById<TextView>(R.id.dishAmount)
        private val minusButton = itemView.findViewById<ImageView>(R.id.minusButton)
        private val plusButton = itemView.findViewById<ImageView>(R.id.plusButton)
        fun bind(cartItem: CartItem) {
            picasso.load(cartItem.dish.imageUrl).into(dishImage)
            dishName.text = cartItem.dish.name
            dishPrice.text = context.getString(R.string.price, cartItem.dish.price)
            dishWeight.text = context.getString(R.string.weight, cartItem.dish.weight)
            dishAmount.text = "${cartItem.quantity}"
            plusButton.setOnClickListener {
                onPlusClick(cartItem)
            }
            minusButton.setOnClickListener {
                onMinusClick(cartItem)
            }
        }
    }
}