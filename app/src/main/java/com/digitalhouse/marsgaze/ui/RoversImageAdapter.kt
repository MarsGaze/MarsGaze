package com.digitalhouse.marsgaze.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.marsgaze.R
import com.squareup.picasso.Picasso

class RoversImageAdapter(
    val context: Context,
    var imageList: ArrayList<RoversImageItem>,
    var imageListener: OnItemClickListener
) :
    RecyclerView.Adapter<RoversImageAdapter.ImageViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    class ImageViewHolder(
        itemView: View, listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(itemView) {
        val imageResult: ImageView = itemView.findViewById(R.id.image_result)

        init {
            itemView.setOnClickListener {
                if (listener != null) {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rovers_image_item, parent, false)

        return ImageViewHolder(view, imageListener)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentItem = imageList[position]
        val imgUrl = currentItem.imageUrl

        Picasso.get().load(imgUrl).fit().centerCrop().into(holder.imageResult)
    }

    override fun getItemCount() = imageList.size
}
