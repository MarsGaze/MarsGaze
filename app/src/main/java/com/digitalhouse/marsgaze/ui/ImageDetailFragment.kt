package com.digitalhouse.marsgaze.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.navArgs
import com.digitalhouse.marsgaze.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_image_detail.*

class ImageDetailFragment : Fragment() {

    val args: ImageDetailFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val imageUrl = args.imageUrl

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_image_detail, container, false)

        val detailImageView: ImageView = view.findViewById(R.id.iv_fullImage)
        Picasso.get().load(imageUrl).fit().centerInside().into(detailImageView)

        return view
    }

}