package com.example.apptestproject.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.apptestproject.R

class TagAdapter(private var tags: List<String> = emptyList()) : RecyclerView.Adapter<TagAdapter.TagViewHolder>() {
private var selectedItemPosition: Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_tag, parent, false)
        return TagViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TagViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val tag = tags[position]
        holder.tagTextView.text = tag
        if (position == selectedItemPosition) {
            holder.tagTextView.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
            holder.tagTextView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.tag_active))
        } else {
            holder.tagTextView.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
            holder.tagTextView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.tag_inactive))
        }
        holder.itemView.setOnClickListener {
            selectedItemPosition = position
            notifyDataSetChanged()
        }
        holder.bind(tag)
    }

    override fun getItemCount(): Int {
        return tags.size
    }

    inner class TagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val tagTextView: TextView = itemView.findViewById(R.id.tagName)

        fun bind(tag: String) {
            tagTextView.text = tag
        }
    }
    fun setSelectedItemPosition(position: Int) {
        selectedItemPosition = position
        notifyDataSetChanged()
    }


    fun updateTags(newTags: List<String>) {
        tags = newTags
        notifyDataSetChanged()
    }
}
