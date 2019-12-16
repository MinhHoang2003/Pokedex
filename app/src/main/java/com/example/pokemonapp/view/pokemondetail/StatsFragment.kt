package com.example.pokemonapp.view.pokemondetail


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapp.R
import com.example.pokemonapp.data.model.PokemonDetailModel

/**
 * A simple [Fragment] subclass.
 */
class StatsFragment : Fragment() {

    lateinit var textHP_index: TextView
    lateinit var progressHP: ProgressBar
    lateinit var textATK_index: TextView
    lateinit var progressATK: ProgressBar
    lateinit var textDEF_index: TextView
    lateinit var progressDEF: ProgressBar
    lateinit var textSATK_index: TextView
    lateinit var progressSATK: ProgressBar
    lateinit var textSDEF_index: TextView
    lateinit var progressSDEF: ProgressBar
    lateinit var textSPD_index: TextView
    lateinit var progressSPD: ProgressBar
    lateinit var recyclerWeaknesses: RecyclerView
    lateinit var recyclerAbilities: RecyclerView
    lateinit var pokemonDetailViewModel: PokemonDetailViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_stats_fragment, container, false)
        init(view)
        val pokemonId = arguments?.getString("pokemon_id")
        pokemonId?.let {
            getData(it, context!!)
        }
        return view
    }

    private fun init(view: View) {
        textHP_index = view.findViewById(R.id.textHP_index)
        textATK_index = view.findViewById(R.id.textATK_index)
        textDEF_index = view.findViewById(R.id.textDEF_index)
        textSATK_index = view.findViewById(R.id.textSATK_index)
        textSDEF_index = view.findViewById(R.id.textSDEF_index)
        textSPD_index = view.findViewById(R.id.textSPD_index)
        progressHP = view.findViewById(R.id.progressHP)
        progressATK = view.findViewById(R.id.progressATK)
        progressDEF = view.findViewById(R.id.progressDEF)
        progressSATK = view.findViewById(R.id.progressSATK)
        progressSDEF = view.findViewById(R.id.progressSDEF)
        progressSPD = view.findViewById(R.id.progressSPD)
        recyclerWeaknesses = view.findViewById(R.id.recyclerWeaknesses)
        pokemonDetailViewModel = ViewModelProviders.of(this).get(PokemonDetailViewModel::class.java)
    }

    private fun getData(id: String, context: Context) {
        pokemonDetailViewModel.getPokemonDetail(id)
        pokemonDetailViewModel.pokemonDetail.observe(this, Observer {
            setupView(it, context)
        })
    }

    private fun setupView(pokemonDetail: PokemonDetailModel, context: Context) {
        val stast = pokemonDetail.stats
        val basicStats = stast.basicStatses
        basicStats.forEach {
            when (it.name) {
                "HP" -> {
                    progressHP.max = it.maxValue
                    textHP_index.text = it.value.toString()
                    progressHP.progress = it.value
                }
                "ATK" -> {
                    progressHP.max = it.maxValue
                    textATK_index.text = it.value.toString()
                    progressATK.progress = it.value
                }
                "DEF" -> {
                    progressHP.max = it.maxValue
                    textDEF_index.text = it.value.toString()
                    progressDEF.progress = it.value
                }
                "S.ATK" -> {
                    progressHP.max = it.maxValue
                    textSATK_index.text = it.value.toString()
                    progressSATK.progress = it.value
                }

                "S.DEF" -> {
                    progressHP.max = it.maxValue
                    textSDEF_index.text = it.value.toString()
                    progressSDEF.progress = it.value
                }
                "SPD" -> {
                    progressHP.max = it.maxValue
                    textSPD_index.text = it.value.toString()
                    progressSPD.progress = it.value
                }
            }
        }

        val weaknesses = stast.weaknesses
        Log.d("test","${weaknesses.size}")
        val weaknessesAdapter = WeaknessesAdapter(weaknesses, context)
        recyclerWeaknesses.adapter = weaknessesAdapter
        recyclerWeaknesses.layoutManager = GridLayoutManager(context, 3)
    }

}
