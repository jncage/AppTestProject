package com.example.apptestproject.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.apptestproject.R
import com.example.apptestproject.models.Dish
import com.squareup.picasso.Picasso
import javax.inject.Inject

class DishAdapter(
    private val picasso: Picasso,
    private var dishes: List<Dish> = emptyList(),
    private val onDishClick: (Dish) -> Unit
) :
    RecyclerView.Adapter<DishAdapter.DishViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_dish, parent, false)
        return DishViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return dishes.size
    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        val dish = dishes[holder.adapterPosition]
        holder.bind(dish)
    }

    fun updateDishes(dishes: List<Dish>) {
        this.dishes = dishes
        notifyDataSetChanged()
    }


    inner class DishViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dishImageView = itemView.findViewById<ImageView>(R.id.dishImage)
        private val dishName = itemView.findViewById<TextView>(R.id.dishName)
        fun bind(item: Dish) {
            dishName.text = item.name
            picasso.load(item.imageUrl).into(dishImageView)
            itemView.setOnClickListener {
                onDishClick(item)
            }
        }
    }
}