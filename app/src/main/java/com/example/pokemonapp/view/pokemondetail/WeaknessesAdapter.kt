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
import com.example.pokemonapp.Type
import com.example.pokemonapp.data.model.Weakness

class WeaknessesAdapter(val data: List<Weakness>, private val context: Context) :
    RecyclerView.Adapter<WeaknessesAdapter.Holder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.weakness_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.effect.text = "${data[position].effect}x"
        Glide.with(context).load(Type.TYPE[data[position].name]).into(holder.image)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val effect: TextView = itemView.findViewById(R.id.textIndex)
    }

}