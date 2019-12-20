package com.example.pokemonapp.view.home

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
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
import com.example.pokemonapp.view.pokemondetail.PokemonDetailActivity
import com.example.pokemonapp.view.pokemondetail.WeaknessesAdapter


class PokemonsFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    private var type: String? = HomeActivity.TAG_POKEMON
    private lateinit var homeViewModel: HomeViewModel
    val isLoading = MutableLiveData<Boolean>()
    var pokemonsAdapter: PokemonsAdapter? = null
    var isLoadMore: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_home_fragmen, container, false)
        homeViewModel = activity?.run {
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        }!!
        recyclerView = view.findViewById(R.id.recyclerMain)
        observerData(context!!)
        initScrollListener()
        return view
    }


    private fun observerData(context: Context) {

        homeViewModel.isLoading.observe(this, Observer {
            isLoading.value = it
        })
        homeViewModel.getPokemons(1)
        homeViewModel.pokemons.observe(this, Observer { it ->
            if (pokemonsAdapter == null) {
                pokemonsAdapter = PokemonsAdapter(it, context)
                val linearLayoutManager = LinearLayoutManager(context)
                recyclerView.layoutManager = linearLayoutManager
                recyclerView.adapter = pokemonsAdapter
            } else {
                val data = pokemonsAdapter!!.data.pokemons
                val scrollPosition = data.size - 1
                data.removeAt(data.size - 1)
                pokemonsAdapter!!.notifyItemRemoved(data.size)
                data.addAll(it.pokemons)
                pokemonsAdapter!!.notifyDataSetChanged(

                )
                Log.d(
                    "load_more",
                    "in adapter ${pokemonsAdapter!!.data.pokemons.size} focus on $scrollPosition"
                )
//                recyclerView.scrollToPosition(scrollPosition - 1)
                isLoadMore = false
            }
            pokemonsAdapter?.onItemsClick = {
                val intent = Intent(getContext(), PokemonDetailActivity::class.java)
                intent.putExtra("pokemon_id", it?.id)
                startActivity(intent)
            }
            pokemonsAdapter?.onLongClick = {
                it?.id?.let { it1 -> showDialog(context, it1) }
            }

        })

        homeViewModel.query.observe(this,
            Observer<String> { t ->
                if (isLoading.value == false) {
                    pokemonsAdapter?.filter?.filter(t)
                }
            })
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
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
    }

    private fun initScrollListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager: LinearLayoutManager =
                    recyclerView.layoutManager as LinearLayoutManager
                Log.d("load_more", "in scroll listener")
                val dataSize: Int? = pokemonsAdapter?.data?.pokemons?.size
                val total: Int? = pokemonsAdapter?.data?.total
                if (dataSize != null && total != null &&
                    linearLayoutManager.findLastCompletelyVisibleItemPosition() == dataSize - 1 &&
                    dataSize < total
                ) {
                    Log.d("load_more", "in scroll listener $dataSize $total")
                    if (!isLoadMore) {
                        loadMore()
                        isLoadMore = true
                    }
                }
            }
        })
    }

    private fun loadMore() {
        Log.d("load_more", "in load more fun")
        pokemonsAdapter!!.data.pokemons.add(null)
        pokemonsAdapter!!.notifyItemInserted(pokemonsAdapter!!.data.pokemons.size - 1)
        Handler().postDelayed({
            homeViewModel.getPokemons(pokemonsAdapter!!.currentPage++)
        }, 1000)
    }
}
