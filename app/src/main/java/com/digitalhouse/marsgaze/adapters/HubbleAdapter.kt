package com.digitalhouse.marsgaze.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.models.hubble.HubbleResponse
import com.squareup.picasso.Picasso

class HubbleAdapter(
    var adapterHubbleList: HubbleResponse,
    private var itemClickListener: OnItemClickListener,
) :
    RecyclerView.Adapter<HubbleAdapter.ImageViewHolder>() {

    // Must be implemented by the corresponding fragment overriding onItemClick
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    inner class ImageViewHolder(
        itemView: View, listener: OnItemClickListener
    ) : RecyclerView.ViewHolder(itemView) {
        val hubbleResult: ImageView = itemView.findViewById(R.id.hubble_result)

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
            .inflate(R.layout.item_hubble, parent, false)

        return ImageViewHolder(view, itemClickListener)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentItem = adapterHubbleList.collection.items[position]
        val hubbleUrl = currentItem.links[0].linkHref

        Picasso.get().load(hubbleUrl).fit().centerCrop().into(holder.hubbleResult)
    }

    override fun getItemCount() = adapterHubbleList.collection.items.size
}