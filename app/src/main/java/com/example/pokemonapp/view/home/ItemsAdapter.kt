package com.example.pokemonapp.view.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokemonapp.R
import com.example.pokemonapp.data.model.Item
import com.example.pokemonapp.data.model.Items

class ItemsAdapter(val data: Items, private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_ITEM = 0
        const val LOAD_ITEM = 1
    }

    var currentPage = 1


    internal var filterListResult: List<Item?>


    init {
        filterListResult = data.items
    }

    var onItemsClick: ((Item?) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_ITEM) {
            val itemView =
                LayoutInflater.from(context).inflate(R.layout.layout_items_tem, parent, false)
            ContentViewHolder(itemView)
        } else {
            val itemView =
                LayoutInflater.from(context).inflate(R.layout.loadmore_item, parent, false)
            ContentLoadingViewHolder(itemView)
        }
    }

    override fun getItemCount(): Int {
        return data.items.size
    }

    override fun getItemViewType(position: Int): Int {
        if (data.items[position] == null) return LOAD_ITEM
        return VIEW_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ContentViewHolder) {
            val item = filterListResult[position]
            if (item != null) {
                holder.name.text = item.name
                holder.price.text = item.price.toString()
                Glide.with(context).load(item.image).into(holder.image)
            }
        }
    }

    inner class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imageBall)
        val name: TextView = itemView.findViewById(R.id.textItemName)
        val price: TextView = itemView.findViewById(R.id.textItemPrice)

        init {
            itemView.setOnClickListener {
                onItemsClick?.invoke(data.items[adapterPosition])
            }
        }
    }

    inner class ContentLoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


}