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
    RecyclerView.Adapter<PokemonsAdapter.ViewHolder>(), Filterable {

    internal var filterListResult: List<Pokemon> = data.pokemons


    var onItemsClick: ((Pokemon) -> Unit)? = null
    var onLongClick: ((Pokemon) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.layout_pokemon_items, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return filterListResult.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = filterListResult[position]
        holder.name.text = pokemon.name
        holder.id.text = pokemon.id
        val glide = Glide.with(context)
        glide.load(pokemon.image).into(holder.image)
        if (pokemon.pokemonTypes.size >= 2) {
            glide.load(Type.TYPE[pokemon.pokemonTypes[0]]).into(holder.imageType01)
            glide.load(Type.TYPE[pokemon.pokemonTypes[1]]).into(holder.imageType02)
        } else if (pokemon.pokemonTypes.size == 1) {
            glide.load(Type.TYPE[pokemon.pokemonTypes[0]]).into(holder.imageType01)
            holder.imageType02.visibility = View.INVISIBLE
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val searchQuery = p0.toString()
                filterListResult = if (searchQuery.isEmpty()) {
                    data.pokemons
                } else {
                    val result = ArrayList<Pokemon>()
                    for (item in data.pokemons) {
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
                filterListResult = p1?.values as List<Pokemon>
                notifyDataSetChanged()
            }

        }
    }
}