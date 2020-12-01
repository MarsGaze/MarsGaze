package com.digitalhouse.marsgaze.adapters
// pew, pew, pew
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.objects.RoverResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rovers_image_item.view.*

class RoversImageAdapter(
    var adapterImageList: RoverResponse,
    private var imageListener: OnItemClickListener
) :
    RecyclerView.Adapter<RoversImageAdapter.ImageViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

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

        return ImageViewHolder(view, imageListener)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentItem = adapterImageList.photos[position]
        val imgUrl = currentItem.imageUrl

        Picasso.get().load(imgUrl).fit().centerCrop().into(holder.imageResult)
    }

    override fun getItemCount() = adapterImageList.photos.size
}
