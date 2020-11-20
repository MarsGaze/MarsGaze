package com.digitalhouse.marsgaze.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.marsgaze.R
import com.squareup.picasso.Picasso

class RoversImageAdapter(val context: Context, var imageList: ArrayList<RoversImageItem>) :
    RecyclerView.Adapter<RoversImageAdapter.ImageViewHolder>() {

    class ImageViewHolder(
        itemView: View,
    ) : RecyclerView.ViewHolder(itemView) {
        val imageResult: ImageView = itemView.findViewById(R.id.image_result)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rovers_image_item, parent, false)

        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentItem = imageList[position]
        val imgUrl = currentItem.imageUrl

        Picasso.get().load(imgUrl).fit().centerCrop().into(holder.imageResult)
    }

    override fun getItemCount() = imageList.size
}
