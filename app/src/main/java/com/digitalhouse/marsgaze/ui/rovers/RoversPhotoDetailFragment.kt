package com.digitalhouse.marsgaze.ui.rovers
// pew, pew, pew
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.digitalhouse.marsgaze.databinding.FragmentImageDetailBinding
import com.digitalhouse.marsgaze.ui.rovers.RoversPhotoDetailFragmentArgs
import com.squareup.picasso.Picasso

// TODO: Change layout name
class RoversPhotoDetailFragment : Fragment() {
    private val args: RoversPhotoDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentImageDetailBinding

    /**
     * In Android Jetpack, ViewBinding is replacing 'kotlin-android-extension' synthetics
     * https://developer.android.com/topic/libraries/view-binding
     *
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageDetailBinding.inflate(inflater, container, false)

        val photo = args.roverPhoto

        binding.tvInfoTitle.text = "Sol ${photo.sol}"
        binding.tvInfoImgCamera.text = "${photo.camera.abbrName} - ${photo.camera.fullName}"
        binding.tvInfoImgEarthDate.text = photo.earthDate

        binding.expandButton.setOnClickListener {
            when (binding.groupInfo.visibility) {
                View.VISIBLE -> {
                    binding.groupInfo.visibility = View.GONE
                    TransitionManager.beginDelayedTransition(
                        binding.infoCard,
                        AutoTransition()
                    )
                    binding.expandButton.animate().rotationX(180F)
                }
                else -> {
                    binding.groupInfo.visibility = View.VISIBLE
                    TransitionManager.beginDelayedTransition(
                        binding.infoCard,
                        AutoTransition()
                    )
                    binding.expandButton.animate().rotationX(0F)
                }
            }
        }

        val detailImageView: ImageView = binding.ivFullImage
        Picasso.get().load(photo.imageUrl).fit().centerInside().into(detailImageView)

        return binding.root
    }

}