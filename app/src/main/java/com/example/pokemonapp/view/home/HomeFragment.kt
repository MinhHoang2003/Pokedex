package com.example.pokemonapp.view.home

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokemonapp.R
import com.example.pokemonapp.Type
import com.example.pokemonapp.view.itemdetail.ItemDetailActivity
import com.example.pokemonapp.view.movedetail.MoveDetailActivity
import com.example.pokemonapp.view.pokemondetail.PokemonDetailActivity
import com.example.pokemonapp.view.pokemondetail.WeaknessesAdapter


class HomeFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    private var type: String? = HomeActivity.TAG_POKEMON
    private lateinit var homeViewModel: HomeViewModel
    val isLoading = MutableLiveData<Boolean>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_home_fragmen, container, false)
        homeViewModel = activity?.run {
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        }!!
        Log.d("test", "$homeViewModel")
        type = arguments?.getString("type")
        recyclerView = view.findViewById(R.id.recyclerMain)
        setupRecyclerView(context!!)
        return view
    }


    private fun setupRecyclerView(context: Context) {
//        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        homeViewModel.isLoading.observe(this, Observer {
            isLoading.value = it
        })
        Log.d("test", type)
        when (type) {
            HomeActivity.TAG_POKEMON -> {
                var pokemonsAdapter: PokemonsAdapter? = null
                homeViewModel.getPokemons()
                homeViewModel.pokemons.observe(this, Observer { it ->
                    pokemonsAdapter = PokemonsAdapter(it, context)
                    pokemonsAdapter?.onItemsClick = {
                        val intent = Intent(getContext(), PokemonDetailActivity::class.java)
                        intent.putExtra("pokemon_id", it.id)
                        startActivity(intent)
                    }
                    pokemonsAdapter?.onLongClick = {
                        showDialog(context, it.id)
                    }
                    recyclerView.adapter = pokemonsAdapter
                    recyclerView.layoutManager = LinearLayoutManager(context)
                })

                homeViewModel.query.observe(this, Observer {
                    pokemonsAdapter?.filter?.filter(it)
                })
            }
            HomeActivity.TAG_ITEMS -> {
                var itemsAdapter: ItemsAdapter? = null
                homeViewModel.getItems()
                homeViewModel.items.observe(this, Observer { it ->
                    itemsAdapter = ItemsAdapter(it, context)
                    recyclerView.adapter = itemsAdapter
                    itemsAdapter?.onItemsClick = {
                        val intent = Intent(getContext(), ItemDetailActivity::class.java)
                        intent.putExtra("item_name", it.name)
                        startActivity(intent)
                    }
                    recyclerView.layoutManager = LinearLayoutManager(context)
                })
                homeViewModel.query.observe(this, Observer {
                    itemsAdapter?.filter?.filter(it)
                })
            }
            HomeActivity.TAG_MOVES -> {
                var movesAdapter: MovesAdapter? = null
                homeViewModel.getMoves()
                homeViewModel.moves.observe(this, Observer { it ->
                    Log.d("load", "start load move fragment")
                    movesAdapter = MovesAdapter(it, context)
                    movesAdapter?.onItemsClick = {
                        val intent = Intent(getContext(), MoveDetailActivity::class.java)
                        intent.putExtra("move_name", it.name)
                        startActivity(intent)
                    }
                    recyclerView.adapter = movesAdapter
                    recyclerView.layoutManager = LinearLayoutManager(context)
                })

                homeViewModel.query.observe(this, Observer {
                    movesAdapter?.filter?.filter(it)
                })
            }
        }
    }

    private fun showDialog(context: Context, id: String) {
        val dialog = Dialog(context)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.layout_dialog_pokemon_preview)
        val pkName: TextView = dialog.findViewById(R.id.textPokemonName)
        val pkId: TextView = dialog.findViewById(R.id.textPokemonId)
        val image: ImageView = dialog.findViewById(R.id.imagePokemonImage)
        val type: ImageView = dialog.findViewById(R.id.imagePokemonType)
        val recyclerView: RecyclerView = dialog.findViewById(R.id.recyclerPreview)
        homeViewModel.getPokemon(id)
        homeViewModel.pokemon.observe(this, Observer {
            pkName.text = it.pokemon.name
            pkId.text = it.pokemon.id
            val glide = Glide.with(context)
            glide.load(it.pokemon.image).into(image)
            glide.load(Type.TYPE[it.pokemon.pokemonTypes[0]]).into(type)
            val weaknessesAdapter = WeaknessesAdapter(it.stats.weaknesses, context)
            recyclerView.adapter = weaknessesAdapter
            recyclerView.layoutManager = GridLayoutManager(activity, 3)
        })
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
    }

}
