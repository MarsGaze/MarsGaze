package com.digitalhouse.marsgaze.ui
// pew, pew, pew
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.digitalhouse.marsgaze.databinding.FragmentImageDetailBinding
import com.squareup.picasso.Picasso

class ImageDetailFragment : Fragment() {
    private val args: ImageDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentImageDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageDetailBinding.inflate(inflater, container, false)

        val imageUrl = args.imageUrl
        val sol = "Sol ${args.sol}"
        val cameraText = "${args.cameraAbrr} - ${args.cameraFull}"
        val earthDate = "Data: ${args.earthDate}"

        binding.tvInfoSol.text = sol
        binding.tvInfoImgCamera.text = cameraText
        binding.tvInfoImgEarthDate.text = earthDate

        val detailImageView: ImageView = binding.ivFullImage
        Picasso.get().load(imageUrl).fit().centerInside().into(detailImageView)

        return binding.root
    }

}