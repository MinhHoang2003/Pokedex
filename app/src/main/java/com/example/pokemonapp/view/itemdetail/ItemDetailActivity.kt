package com.example.pokemonapp.view.itemdetail

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.pokemonapp.R
import com.example.pokemonapp.data.model.Item

class ItemDetailActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var imageBall: ImageView
    lateinit var textBallName: TextView
    lateinit var textBallPrice: TextView
    lateinit var textBallDescription: TextView
    lateinit var itemDetailViewModel: ItemDetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        initView()
        itemDetailViewModel = ViewModelProviders.of(this).get(ItemDetailViewModel::class.java)
        val itemName = intent.getStringExtra("item_name")
        itemName?.let {
            getData(it)
        }
    }

    private fun initView() {
        toolbar = findViewById(R.id.toolBar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationIcon(R.drawable.icon_down_white)
        toolbar.title = ""
        imageBall = findViewById(R.id.imageBall)
        textBallName = findViewById(R.id.textBallName)
        textBallPrice = findViewById(R.id.textBallPrice)
        textBallDescription = findViewById(R.id.textBallDescription)
    }

    private fun getData(name: String) {
        if (name.isNotEmpty()) {
            itemDetailViewModel.getItem(name)
            itemDetailViewModel.item.observe(this, Observer {
                setData(it)
            })
        }
    }

    private fun setData(item: Item) {
        Glide.with(this).load(item.image).into(imageBall)
        textBallName.text = item.name
        textBallPrice.text = item.price.toString()
        textBallDescription.text = item.effects
    }
}
