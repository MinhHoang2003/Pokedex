package com.example.pokemonapp.view.pokemondetail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapp.R

/**
 * A simple [Fragment] subclass.
 */
class EvolutionFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var pokemonDetailViewModel: PokemonDetailViewModel
    var _tag: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_evolution_fragment, container, false)
        val pokemonId = arguments?.getString("pokemon_id")
        _tag = arguments?.getString("tag")
        recyclerView = view.findViewById(R.id.recyclerRevolution)
        pokemonDetailViewModel = ViewModelProviders.of(this).get(PokemonDetailViewModel::class.java)
        pokemonId?.let {
            getData(it, _tag)
        }
        return view
    }

    private fun getData(id: String, tag: String?) {
        if (id.isNotEmpty() && tag != null) {
            pokemonDetailViewModel.getPokemonDetail(id)
            if (tag == "Evolution") {
                pokemonDetailViewModel.pokemonDetail.observe(this, Observer {
                    val evolution = it.evolutions
                    val evolutionAdapter = context?.let { it1 -> EvolutionAdapter(evolution, it1) }
                    recyclerView.layoutManager = LinearLayoutManager(context)
                    recyclerView.adapter = evolutionAdapter
                })
            } else {
                pokemonDetailViewModel.pokemonDetail.observe(this, Observer {
                    val move = it.moves
                    val moveAdapter = context?.let { it1 -> MoveAdapter(move, it1) }
                    recyclerView.layoutManager = LinearLayoutManager(context)
                    recyclerView.adapter = moveAdapter
                })
            }
        }
    }
}
