package com.example.pokemonapp.view.home

import android.content.Context
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
import com.example.pokemonapp.Type
import com.example.pokemonapp.data.model.Move
import com.example.pokemonapp.data.model.Moves

class MovesAdapter(val data: Moves, private val context: Context) :
    RecyclerView.Adapter<MovesAdapter.ViewHolder>(), Filterable {

    internal var filterListResult: List<Move> = data.moves
    var onItemsClick: ((Move) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.layout_move_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return filterListResult.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val move = filterListResult[position]
        holder.name.text = move.name
        Glide.with(context).load(Type.TYPE[move.type]).into(holder.image)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val name: TextView = itemView.findViewById(R.id.textTitle)

        init {
            itemView.setOnClickListener {
                onItemsClick?.invoke(data.moves[adapterPosition])
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val searchQuery = p0.toString()
                filterListResult = if (searchQuery.isEmpty()) {
                    data.moves
                } else {
                    val result = ArrayList<Move>()
                    for (item in data.moves) {
                        if (item.name.toLowerCase().contains(searchQuery.toLowerCase())) {
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
                filterListResult = p1?.values as List<Move>
                notifyDataSetChanged()
            }

        }
    }
}