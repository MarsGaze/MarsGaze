package com.digitalhouse.marsgaze.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.models.hubble.HubbleResponse
import com.squareup.picasso.Picasso

class HubbleAdapter(
    private var itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<HubbleAdapter.ImageViewHolder>() {

    var adapterHubbleList: HubbleResponse = HubbleResponse()

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

        Picasso.get().load(hubbleUrl).placeholder(progressBar).error(progressBar2).fit()
            .centerCrop().into(holder.hubbleResult)

        progressBar.start()
        progressBar2.start()
    }

    override fun getItemCount() = adapterHubbleList.collection.items.size
}