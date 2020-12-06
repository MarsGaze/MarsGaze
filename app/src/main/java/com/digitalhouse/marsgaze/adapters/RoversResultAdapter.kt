package com.digitalhouse.marsgaze.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.models.rovers.RoverResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rovers_image_item.view.*

class RoversResultAdapter(
    var adapterImageList: RoverResponse,
    private var itemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<RoversResultAdapter.ImageViewHolder>() {

    // Must be implemented by the corresponding fragment overriding onItemClick
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    // TODO: Learn how to use ViewBinding instead of kotlin synthetics in this case
    class ImageViewHolder(
        itemView: View, listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(itemView) {
        val imageResult: ImageView = itemView.image_result

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
        val currentItem = adapterImageList.photos[position]
        val imgUrl = currentItem.imageUrl

        Picasso.get().load(imgUrl).fit().centerCrop().into(holder.imageResult)
    }

    override fun getItemCount() = adapterImageList.photos.size
}
