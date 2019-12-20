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

class MovesAdapter(var data: Moves, private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    companion object {
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_LOADING = 1
        var CURRENT_PAGE = 1
    }

    internal var filterListResult: List<Move?>

    init {
        filterListResult = data.moves
    }

    var onItemsClick: ((Move?) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == VIEW_TYPE_ITEM) {
            val itemView =
                LayoutInflater.from(context).inflate(R.layout.layout_move_item, parent, false)
            ContentViewHolder(itemView)
        } else {
            val itemView =
                LayoutInflater.from(context).inflate(R.layout.loadmore_item, parent, false)
            ContentLoadingViewHolder(itemView)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ContentViewHolder) {
            val move = data.moves[position]
            move?.let {
                holder.name.text = move.name
                Glide.with(context).load(Type.TYPE[move.type]).into(holder.image)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (data.moves[position] == null) return VIEW_TYPE_LOADING
        return VIEW_TYPE_ITEM
    }

    override fun getItemCount(): Int {
        return data.moves.size
    }

    inner class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val name: TextView = itemView.findViewById(R.id.textTitle)

        init {
            itemView.setOnClickListener {
                onItemsClick?.invoke(data.moves[adapterPosition])
            }
        }
    }

    inner class ContentLoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val searchQuery = p0.toString()
                filterListResult = if (searchQuery.isEmpty()) {
                    data.moves
                } else {
                    val result = ArrayList<Move?>()
                    for (item in data.moves) {
                        if (item?.name?.toLowerCase()?.contains(searchQuery.toLowerCase())!!) {
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