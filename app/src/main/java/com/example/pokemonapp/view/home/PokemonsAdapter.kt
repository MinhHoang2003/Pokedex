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
import com.example.pokemonapp.data.model.Pokemon
import com.example.pokemonapp.data.model.Pokemons

class PokemonsAdapter(val data: Pokemons, private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    companion object {
        const val VIEW_ITEM = 0
        const val LOAD_ITEM = 1
    }

    var currentPage = 1

    internal var filterListResult: ArrayList<Pokemon?> = ArrayList(data.pokemons)


    var onItemsClick: ((Pokemon?) -> Unit)? = null
    var onLongClick: ((Pokemon?) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_ITEM) {
            val itemView =
                LayoutInflater.from(context).inflate(R.layout.layout_pokemon_items, parent, false)
            ContentViewHolder(itemView)
        } else {
            val itemView =
                LayoutInflater.from(context).inflate(R.layout.loadmore_item, parent, false)
            ContentLoadingViewHolder(itemView)
        }
    }

    override fun getItemCount(): Int {
        return data.pokemons.size
    }

    override fun getItemViewType(position: Int): Int {
        if (data.pokemons[position] == null) return LOAD_ITEM
        return VIEW_ITEM
    }

    inner class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imagePokemon)
        val name: TextView = itemView.findViewById(R.id.textPokemonName)
        val id: TextView = itemView.findViewById(R.id.textPokemonId)
        val imageType01: ImageView = itemView.findViewById(R.id.imageType1)
        val imageType02: ImageView = itemView.findViewById(R.id.imageType2)

        init {
            itemView.setOnClickListener {
                onItemsClick?.invoke(data.pokemons[adapterPosition])
            }
            itemView.setOnLongClickListener {
                onLongClick?.invoke(data.pokemons[adapterPosition])
                false
            }
        }

    }

    inner class ContentLoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val searchQuery = p0.toString()
                filterListResult = if (searchQuery.isEmpty()) {
                    ArrayList(data.pokemons)
                } else {
                    val result = ArrayList<Pokemon?>()
                    for (item in data.pokemons) {
                        if (item!!.name.toLowerCase().contains(searchQuery.toLowerCase())) {
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
                filterListResult.clear()
                filterListResult.addAll(p1?.values as ArrayList<Pokemon>)
                notifyDataSetChanged()
            }

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ContentViewHolder) {
            val pokemon = data.pokemons[position]
            holder.name.text = pokemon?.name
            holder.id.text = pokemon?.id
            val glide = Glide.with(context)
            glide.load(pokemon?.image).into(holder.image)
            if (pokemon?.pokemonTypes?.size!! >= 2) {
                glide.load(Type.TYPE[pokemon.pokemonTypes[0]]).into(holder.imageType01)
                glide.load(Type.TYPE[pokemon.pokemonTypes[1]]).into(holder.imageType02)

            } else if (pokemon.pokemonTypes.size == 1) {
                glide.load(Type.TYPE[pokemon.pokemonTypes[0]]).into(holder.imageType01)
                holder.imageType02.visibility = View.INVISIBLE
            }
        }
    }
}