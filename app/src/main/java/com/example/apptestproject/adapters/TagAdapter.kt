package com.example.apptestproject.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.apptestproject.R

class TagAdapter(
    private var tags: List<String> = emptyList(), private val onTagClick: (String) -> Unit
) :
    RecyclerView.Adapter<TagAdapter.TagViewHolder>() {
    private var selectedItemPosition: Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_tag, parent, false)
        return TagViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        val tag = tags[position]
        holder.bind(tag)
    }

    override fun getItemCount(): Int = tags.size

    inner class TagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tagTextView: TextView = itemView.findViewById(R.id.tagName)

        fun bind(tag: String) {
            val context = itemView.context
            val isSelected = adapterPosition == selectedItemPosition
            val textColor =
                ContextCompat.getColor(context, if (isSelected) R.color.white else R.color.black)
            val bgTintList = ContextCompat.getColorStateList(
                context,
                if (isSelected) R.color.color_active else R.color.color_inactive
            )

            with(tagTextView) {
                text = tag
                setTextColor(textColor)
                backgroundTintList = bgTintList
            }

            itemView.setOnClickListener {
                if (adapterPosition != selectedItemPosition) {
                    val previousSelectedPosition = selectedItemPosition
                    selectedItemPosition = adapterPosition
                    notifyItemChanged(previousSelectedPosition)
                    notifyItemChanged(selectedItemPosition)
                    onTagClick(tag)
                }
            }

        }
    }

    fun setSelectedItemPosition(position: Int) {
        if (selectedItemPosition != position) {
            val previousItemPosition = selectedItemPosition
            selectedItemPosition = position
            notifyItemChanged(previousItemPosition)
            notifyItemChanged(selectedItemPosition)
        }
    }

    fun updateTags(newTags: List<String>) {
        tags = newTags
        notifyDataSetChanged()
    }
}
