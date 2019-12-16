package com.example.pokemonapp.view.pokemondetail

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.pokemonapp.R
import com.example.pokemonapp.data.model.PokemonDetailModel
import com.example.pokemonapp.inTransaction
import com.google.android.material.appbar.AppBarLayout

class PokemonDetailActivity : AppCompatActivity() {

    lateinit var radioTagGroup: RadioGroup
    lateinit var appBarLayout: AppBarLayout
    lateinit var toolbar: Toolbar
    lateinit var toolbarTitle: TextView
    lateinit var nestedScrollView: NestedScrollView
    lateinit var imagePokemon: ImageView
    lateinit var textPokemonName: TextView
    lateinit var imagePokemonTag: ImageView
    lateinit var textPokemonDetail: TextView
    private var pokemonDetailViewModel: PokemonDetailViewModel? = null
    private var pokemonId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_detail_activity)
        pokemonId = intent.getStringExtra("pokemon_id")
        init()
        pokemonId?.let {
            getData(it)
            loadFragment(StatsFragment(), it, "Stats")
        }
    }

    private fun getData(id: String) {
        pokemonDetailViewModel?.getPokemonDetail(id)
        pokemonDetailViewModel?.pokemonDetail?.observe(this, Observer {
            toolbarTitle.text = it.pokemon.name
            setupView(it)
        })
    }

    private fun setupView(pokemonDetail: PokemonDetailModel?) {
        val glide = Glide.with(this)
        pokemonDetail?.let {
            glide.load(pokemonDetail.pokemon.image).into(imagePokemon)
            textPokemonName.text = pokemonDetail.pokemon.name
            textPokemonDetail.text = pokemonDetail.pokemon.description
        }
    }

    private fun init() {
        toolbar = findViewById(R.id.toolBar)
        appBarLayout = findViewById(R.id.appBarLayout)
        nestedScrollView = findViewById(R.id.nestScrollView)
        setSupportActionBar(toolbar)
        toolbarTitle = toolbar.findViewById(R.id.textToolbarTitle)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationIcon(R.drawable.icon_down_white)
        toolbar = findViewById(R.id.toolBar)
        radioTagGroup = findViewById(R.id.tagGroup)
        imagePokemon = findViewById(R.id.imagePokemon)
        textPokemonName = findViewById(R.id.textPokemonName)
        imagePokemonTag = findViewById(R.id.imagePokemonTag)
        textPokemonDetail = findViewById(R.id.textPokemonDetail)
        pokemonDetailViewModel = ViewModelProviders.of(this).get(PokemonDetailViewModel::class.java)

        radioTagGroup.setOnCheckedChangeListener { _, id ->
            when (id) {
                R.id.radio0 -> {
                    pokemonId?.let { loadFragment(StatsFragment(), it, "Stat") }
                }
                R.id.radio1 -> {
                    pokemonId?.let { loadFragment(EvolutionFragment(), it, "Evolution") }
                }
                R.id.radio2 -> {
                    pokemonId?.let { loadFragment(EvolutionFragment(), it, "Moves") }
                }
            }
        }
        appBarLayout.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State) {
                when (state) {
                    State.COLLAPSED -> {
                        nestedScrollView.background = getDrawable(R.drawable.background_conner)
                        toolbarTitle.visibility = View.VISIBLE
                    }
                    else -> {
                        toolbarTitle.visibility = View.INVISIBLE
                        nestedScrollView.background = getDrawable(R.color.white)
                    }
                }
            }

        })

    }

    private fun loadFragment(fragment: Fragment, id: String, tag: String) {
        val bundle = Bundle()
        bundle.putString("pokemon_id", id)
        bundle.putString("tag", tag)
        fragment.arguments = bundle
        supportFragmentManager.inTransaction {
            setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
            replace(R.id.fragmentContainer, fragment, tag)
        }
    }
}
