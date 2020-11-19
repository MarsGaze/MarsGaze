package com.digitalhouse.marsgaze.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.adapters.FavoriteAdapter
import com.digitalhouse.marsgaze.objects.Favorite
import kotlinx.android.synthetic.main.activity_favoritos.*

class FavoriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoritos)


        val adapter = FavoriteAdapter(Favorite.generator())

        recyFav.setHasFixedSize(true)
        recyFav.adapter = adapter
        recyFav.layoutManager = LinearLayoutManager(this)

    }
}