package com.digitalhouse.marsgaze.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.models.rovers.RoverResponse
import com.squareup.picasso.Picasso

class RoversResultAdapter(
    private var itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RoversResultAdapter.ImageViewHolder>() {

    var roverResponse: RoverResponse = RoverResponse()

    // Must be implemented by the corresponding fragment overriding onItemClick
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    class ImageViewHolder(
        itemView: View, listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(itemView) {
        val imageResult: ImageView = itemView.findViewById(R.id.image_result)

        init {
            itemView.setOnClickListener {
                val position = absoluteAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rovers_image_item, parent, false)

        return ImageViewHolder(view, itemClickListener)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentItem = roverResponse.photos[position]
        val imgUrl = currentItem.imageUrl

        val progressBar = CircularProgressDrawable(holder.itemView.context)
        progressBar.strokeWidth = 7f
        progressBar.centerRadius = 90f

        val accent = ContextCompat.getColor(holder.itemView.context, R.color.colorAccent)
        val white = ContextCompat.getColor(holder.itemView.context, R.color.colorWhite)
        progressBar.setColorSchemeColors(accent, white)

        val progressBar2 = CircularProgressDrawable(holder.itemView.context)
        progressBar2.strokeWidth = 7f
        progressBar2.centerRadius = 90f
        progressBar2.setColorSchemeColors(accent, white)

        Picasso.get().load(imgUrl).placeholder(progressBar).error(progressBar2).fit().centerCrop()
            .into(holder.imageResult)
        progressBar.start()
        progressBar2.start()
    }

    override fun getItemCount() = roverResponse.photos.size
}
