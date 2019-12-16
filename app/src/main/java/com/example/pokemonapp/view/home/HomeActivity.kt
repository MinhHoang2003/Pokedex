package com.example.pokemonapp.view.home

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.pokemonapp.R
import com.example.pokemonapp.hideKeyboard
import com.example.pokemonapp.inTransaction
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.*

class HomeActivity : AppCompatActivity() {

    lateinit var main: ConstraintLayout
    lateinit var progress: ProgressBar
    lateinit var bottomNavigation: BottomNavigationView
    lateinit var searchContainer: EditText
    lateinit var toolbarTitle: TextView
    lateinit var homeViewModel: HomeViewModel
    var currentTag = TAG_POKEMON

    companion object {
        const val TAG_POKEMON = "Pokemon"
        const val TAG_MOVES = "Moves"
        const val TAG_ITEMS = "Items"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_home_activity)
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        initView()
        loadFragment(HomeFragment(), TAG_POKEMON)
    }

    private fun initView() {
        main = findViewById(R.id.mainBackground)
        searchContainer = findViewById(R.id.textSearchHint)
        toolbarTitle = findViewById(R.id.textScreenHeader)
        searchContainer.hideKeyboard()
        bottomNavigation = findViewById(R.id.bottomNavigation)
        progress = findViewById(R.id.progressOnLoading)
        bottomNavigation.setOnNavigationItemSelectedListener {
            val type = when (it.itemId) {
                R.id.menu_move -> TAG_MOVES
                R.id.menu_item -> TAG_ITEMS
                R.id.menu_pokemon -> TAG_POKEMON
                else -> return@setOnNavigationItemSelectedListener false
            }
            loadFragment(HomeFragment(), type)
            return@setOnNavigationItemSelectedListener true
        }
        val gradientDrawableForMainBackground = GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT,
            intArrayOf(
                ContextCompat.getColor(
                    this,
                    R.color.color_1
                ),
                ContextCompat.getColor(
                    this,
                    R.color.color_2
                ),
                ContextCompat.getColor(
                    this,
                    R.color.color_3
                ),
                ContextCompat.getColor(
                    this,
                    R.color.color_4
                )
            )
        )
        main.background = gradientDrawableForMainBackground

        var currentText = ""
        searchContainer.doOnTextChanged { text, start, count, after ->
            CoroutineScope(Dispatchers.IO).launch {
                delay(500)
                if (currentText != text.toString()) {
                    currentText = text.toString()
                    withContext(Dispatchers.Main){
                        homeViewModel.query.value = currentText
                    }
                }
            }
        }

    }

    private fun loadFragment(fragment: Fragment, type: String) {
        currentTag = type
        toolbarTitle.text = currentTag
        if (fragment is HomeFragment) {
            fragment.isLoading.observe(this, Observer {
                if (it) progress.visibility = View.VISIBLE
                else progress.visibility = View.INVISIBLE
            })
        }
        val bundle = Bundle()
        bundle.putString("type", type)
        fragment.arguments = bundle
        supportFragmentManager.inTransaction {
            setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
            replace(R.id.fragment_container, fragment, type)
        }
    }


}
