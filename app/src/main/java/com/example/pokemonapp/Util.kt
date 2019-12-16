package com.example.pokemonapp

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import java.io.ByteArrayOutputStream


class Util : Application() {
    companion object {
        fun convert(source: Resources, id: Int): String? {
            val bmp =
                BitmapFactory.decodeResource(source, id) //your image
            val bYtE = ByteArrayOutputStream()
            bmp.compress(Bitmap.CompressFormat.PNG, 100, bYtE)
            bmp.recycle()
            val byteArray: ByteArray = bYtE.toByteArray()
            return Base64.encodeToString(byteArray, Base64.DEFAULT)

        }

        fun stringToBitMap(encodedString: String?): Bitmap? {
            return try {
                val encodeByte =
                    Base64.decode(encodedString, Base64.DEFAULT)
                BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
            } catch (e: Exception) {
                e.message
                null
            }
        }
    }
}

class Type {
    companion object {
        val TYPE = mapOf(
            "NORMAL" to R.drawable.icon_normal,
            "FIRE" to R.drawable.icon_type_fire,
            "WATER" to R.drawable.icon_type_water,
            "ELECTRIC" to R.drawable.icon_type_electric,
            "GRASS" to R.drawable.icon_type_grass,
            "ICE" to R.drawable.icon_type_ice,
            "FIGHTING" to R.drawable.icon_type_fight,
            "POISON" to R.drawable.icon_type_poison,
            "GROUND" to R.drawable.icon_type_ground,
            "FLYING" to R.drawable.icon_type_flying,
            "PSYCHIC" to R.drawable.icon_type_psychic,
            "BUG" to R.drawable.icon_bug,
            "ROCK" to R.drawable.icon_type_rock,
            "GHOST" to R.drawable.icon_type_ghost,
            "DRAGON" to R.drawable.icon_type_dragon,
            "DARK" to R.drawable.icon_type_dark,
            "STEEL" to R.drawable.icon_type_steel,
            "FAIRY" to R.drawable.icon_type_fairy
        )

        val TAG = mapOf(
            "NORMAL" to R.drawable.tag_normal,
            "FIRE" to R.drawable.tag_fire,
            "WATER" to R.drawable.icon_tag_water,
            "ELECTRIC" to R.drawable.tag_electric,
            "GRASS" to R.drawable.tag_grass,
            "ICE" to R.drawable.tag_ice,
            "FIGHTING" to R.drawable.tag_fight,
            "POISON" to R.drawable.tag_poison,
            "GROUND" to R.drawable.tag_ground,
            "FLYING" to R.drawable.tag_flying,
            "PSYCHIC" to R.drawable.tag_psychic,
            "BUG" to R.drawable.tag_bug,
            "ROCK" to R.drawable.tag_rock,
            "GHOST" to R.drawable.tag_ghost,
            "DRAGON" to R.drawable.tag_dragon,
            "DARK" to R.drawable.tag_dark,
            "STEEL" to R.drawable.tag_steel,
            "FAIRY" to R.drawable.tag_fairy
        )
    }
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
    val fragmentTransaction = beginTransaction()
    fragmentTransaction.func()
    fragmentTransaction.commit()
}

fun getThreadName(funName: String) {
    Log.d("test", "$funName run on ${Thread.currentThread().name}")
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}