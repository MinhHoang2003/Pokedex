package com.example.pokemonapp.view.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokemonapp.R
import com.example.pokemonapp.data.model.Item
import com.example.pokemonapp.data.model.Items

class ItemsAdapter(val data: Items, private val context: Context) :
    RecyclerView.Adapter<ItemsAdapter.ViewHolder>(), Filterable {

    internal var filterListResult: List<Item>

    init {
        filterListResult = data.items
    }

    var onItemsClick: ((Item) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.layout_items_tem, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return filterListResult.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = filterListResult[position]
        holder.name.text = item.name
        holder.price.text = item.price.toString()
        Glide.with(context).load(item.image).into(holder.image)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imageBall)
        val name: TextView = itemView.findViewById(R.id.textItemName)
        val price: TextView = itemView.findViewById(R.id.textItemPrice)

        init {
            itemView.setOnClickListener {
                onItemsClick?.invoke(data.items[adapterPosition])
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val query = p0.toString()
                filterListResult = if (query.isEmpty()) {
                    data.items
                } else {
                    val result = ArrayList<Item>()
                    Log.d("search", p0.toString())
                    for (item in data.items) {
                        if (item.name.toLowerCase().contains(query.toLowerCase())) {
                            Log.d("search", "${item.name} + ${item.name.contains(query)}")
                            result.add(item)
                        }
                    }
                    result
                }
                val filterResult = FilterResults()
                filterResult.values = filterListResult
                return filterResult
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                filterListResult = p1?.values as List<Item>
                notifyDataSetChanged()
            }

        }
    }
}