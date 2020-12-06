package com.digitalhouse.marsgaze.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.adapters.FavoriteAdapter
import com.digitalhouse.marsgaze.models.Favorite
import kotlinx.android.synthetic.main.fragment_favoritos.*

class FavoriteFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favoritos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FavoriteAdapter(Favorite.generator())

        recyFav.setHasFixedSize(true)
        recyFav.adapter = adapter
        recyFav.layoutManager = LinearLayoutManager(context)
    }
}