package com.digitalhouse.marsgaze.ui.hubble

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.navArgs
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.databinding.FragmentDetailHubbleBinding
import com.squareup.picasso.Picasso

class DetailHubbleFragment : Fragment() {
    private val args: DetailHubbleFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailHubbleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailHubbleBinding.inflate(inflater, container, false)

        val imgUrl = args.hubble.links.linkHref
        val title = args.hubble.data.title
        val description = args.hubble.data.description
        val date = args.hubble.data.date_created

        binding.tvHubbleTitle.text = title
        binding.tvHubbleDescription.text = description
        binding.tvHubbleDate.text = date

        val ivDetail: ImageView = binding.ivHubbleFull
        Picasso.get().load(imgUrl).fit().centerCrop().into(ivDetail)

        // Inflate the layout for this fragment
        return binding.root
    }

}