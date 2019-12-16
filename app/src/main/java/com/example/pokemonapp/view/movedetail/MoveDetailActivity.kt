package com.example.pokemonapp.view.movedetail

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.pokemonapp.R
import com.example.pokemonapp.Type
import com.example.pokemonapp.data.model.Move

class MoveDetailActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var moveImage: ImageView
    lateinit var textMoveName: TextView
    lateinit var imageMoveTag: ImageView
    lateinit var textMoveDescription: TextView
    lateinit var textBasePower: TextView
    lateinit var textAccuracy: TextView
    lateinit var textPP: TextView
    lateinit var moveDetailViewModel: MoveDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_move_detail)
        init()
        val name = intent.getStringExtra("move_name")
        name?.let {
            getData(it)
        }
    }

    private fun init() {
        toolbar = findViewById(R.id.toolBar)
        moveImage = findViewById(R.id.imageMove)
        textMoveName = findViewById(R.id.textMoveName)
        imageMoveTag = findViewById(R.id.imageMoveTag)
        textMoveDescription = findViewById(R.id.textMoveDescription)
        textBasePower = findViewById(R.id.textBasePower)
        textAccuracy = findViewById(R.id.textAccuracy)
        textPP = findViewById(R.id.textPP)
        moveDetailViewModel = ViewModelProviders.of(this).get(MoveDetailViewModel::class.java)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationIcon(R.drawable.icon_down_white)
    }

    private fun getData(name: String) {
        if (name.isNotEmpty()) {
            moveDetailViewModel.getMove(name)
            moveDetailViewModel.move.observe(this, Observer {
                setupView(it)
            })
        }
    }

    private fun setupView(move: Move) {
        val glide = Glide.with(this)
        glide.load(Type.TYPE[move.type]).into(moveImage)
        glide.load(Type.TAG[move.type]).into(imageMoveTag)
        textMoveDescription.text = move.effects
        textMoveName.text = move.name
        textBasePower.text = move.power.toString()
        textPP.text = move.pp.toString()
        textAccuracy.text = move.accuracy.toString()

    }

}
