package com.digitalhouse.marsgaze.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.models.data.Favorite


class FavoriteAdapter(val list: ArrayList<Favorite>) : RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        return FavoriteHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.favorite_card, parent, false)
        )
    }


    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        val favoriteDummy = list[position]

        val favoriteText = holder.itemView.findViewById<TextView>(R.id.favoriteText)
        favoriteText.text = holder.itemView.resources.getString(
            R.string.favoriteCardText,
            favoriteDummy.sol,
            favoriteDummy.rover
        )

        val favoriteImage = holder.itemView.findViewById<ImageView>(R.id.favoriteImage)
        favoriteImage.setImageResource(favoriteDummy.image)

        val favoriteButton = holder.itemView.findViewById<ImageButton>(R.id.favoriteButton)
        favoriteButton.setOnClickListener {
            // TODO
        }
    }

    override fun getItemCount(): Int = list.size

    inner class FavoriteHolder(view: View) : RecyclerView.ViewHolder(view)
}