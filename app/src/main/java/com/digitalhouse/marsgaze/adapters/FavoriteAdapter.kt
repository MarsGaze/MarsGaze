package com.digitalhouse.marsgaze.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.databinding.FavoriteCardBinding
import com.digitalhouse.marsgaze.models.data.FavoriteType
import com.digitalhouse.marsgaze.models.favorite.ImageDetailAdapter
import com.squareup.picasso.Picasso


class FavoriteAdapter(val list: ArrayList<ImageDetailAdapter>, val favoriteAction: FavoriteAction) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        return FavoriteHolder(
            FavoriteCardBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }


    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        val favorite = list[position]

        val favoriteText = holder.itemView.findViewById<TextView>(R.id.favoriteText)
        favoriteText.text = ""

        when (favorite.getType()) {
            FavoriteType.ROVERS_IMAGE.ordinal -> {
                favoriteText.text = holder.itemView.resources.getString(
                    R.string.favoriteCardText,
                    favorite.getTitle().toInt(),
                    favorite.getExtraInfo() ?: ""
                )
            }
        }

        val favoriteImage = holder.itemView.findViewById<ImageView>(R.id.favoriteImage)
        Picasso.get().load(favorite.getImg()).into(favoriteImage)

        val favoriteButton = holder.itemView.findViewById<ImageButton>(R.id.favoriteButton)
        favoriteButton.setOnClickListener {
            favoriteAction.favAction(favorite, position, holder.binding)
        }

        holder.binding.favoriteButtonFull.setOnClickListener {
            favoriteAction.favAction(favorite, position, holder.binding)
        }

        holder.binding.favoriteImage.setOnClickListener {
            favoriteAction.favDetail(favorite, holder.binding)
        }
    }

    override fun getItemCount(): Int = list.size

    inner class FavoriteHolder(val binding: FavoriteCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface FavoriteAction {
        fun favAction(favoriteTest: ImageDetailAdapter, position: Int, binding: FavoriteCardBinding)

        fun favDetail(favoriteTest: ImageDetailAdapter, binding: FavoriteCardBinding)
    }
}