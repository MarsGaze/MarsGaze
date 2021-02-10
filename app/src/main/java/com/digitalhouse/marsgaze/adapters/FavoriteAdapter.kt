package com.digitalhouse.marsgaze.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.databinding.FavoriteCardBinding
import com.digitalhouse.marsgaze.models.data.FavoriteType
import com.digitalhouse.marsgaze.models.favorite.ImageDetailAdapter
import com.squareup.picasso.Picasso


class FavoriteAdapter(val list: ArrayList<ImageDetailAdapter>,
                      private val favoriteAction: FavoriteAction) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        val binding = FavoriteCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        val image = binding.favoriteImage

        // Definimos o tamanho da imagem de forma programática após ele ter sido renderizado
        // caso contrario não temos acesso ao tamanho dele de antemão
        image.viewTreeObserver.addOnPreDrawListener( object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                if (image.width > 0 && image.height > 0) {
                    // Revemos esse listener já que não necessitamos mais dele
                    image.viewTreeObserver.removeOnPreDrawListener(this)
                    image.layoutParams.height = (image.width * 0.736024845).toInt()
                    image.requestLayout()
                }

                return true
            }
        })

        return FavoriteHolder(
            binding
        )
    }


    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        val favorite = list[position]

        val favoriteText = holder.itemView.findViewById<TextView>(R.id.favoriteText)
        favoriteText.text = ""

        when (favorite.getType()) {
            FavoriteType.ROVERS_IMAGE.ordinal -> {
                val text = favorite.getExtraInfo()!!
                val splited =  text.split("-")
                val textSplit =splited[2] + "/" + splited[1] + "/" + splited[0]

                favoriteText.text = favorite.getTitle()
            }
            FavoriteType.HUBBLE_IMAGE.ordinal -> {
                favoriteText.text = favorite.getTitle()
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

        val image = holder.binding.favoriteImage

        image.setOnClickListener {
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