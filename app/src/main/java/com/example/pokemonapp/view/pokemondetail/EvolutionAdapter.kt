package com.example.pokemonapp.view.pokemondetail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokemonapp.R
import com.example.pokemonapp.data.model.Evolution

class EvolutionAdapter(val evolution: List<Evolution>, val context: Context) :
    RecyclerView.Adapter<EvolutionAdapter.Holder>() {


    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageFrom = itemView.findViewById<ImageView>(R.id.imagePokemonFrom)
        val imageTo = itemView.findViewById<ImageView>(R.id.imagePokemonTo)
        val level = itemView.findViewById<TextView>(R.id.textLevelDetail)
        val nameFrom = itemView.findViewById<TextView>(R.id.textFrom)
        val nameTo = itemView.findViewById<TextView>(R.id.textTo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.layout_evolution_items, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return evolution.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = evolution[position]
        val glide = Glide.with(context)
        glide.load(item.pokemonImage).into(holder.imageFrom)
        glide.load(item.evolutionpokemonImage).into(holder.imageTo)
        holder.level.text = item.evolutionLevel.toString()
        holder.nameFrom.text = item.pokemonName
        holder.nameTo.text = item.evolutionPokemonName
    }
}