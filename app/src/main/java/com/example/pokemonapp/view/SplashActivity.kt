package com.example.pokemonapp.view

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.pokemonapp.R
import com.example.pokemonapp.view.home.HomeActivity
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
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
        layoutSplash.background = gradientDrawableForMainBackground
        layoutSplash
        CoroutineScope(Dispatchers.Main).launch {
            delay(500)
            val intent = Intent(this@SplashActivity, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}
