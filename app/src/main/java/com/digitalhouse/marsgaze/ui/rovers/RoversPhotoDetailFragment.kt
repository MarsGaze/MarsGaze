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
import com.squareup.picasso.Picasso

// TODO: Change layout name
class RoversPhotoDetailFragment : Fragment() {
    private val args: RoversPhotoDetailFragmentArgs by navArgs()
    private var _binding: FragmentImageDetailBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    /**
     * In Android Jetpack, ViewBinding is replacing 'kotlin-android-extension' synthetics
     * https://developer.android.com/topic/libraries/view-binding
     *
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageDetailBinding.inflate(inflater, container, false)

        // TODO: Pass data as Serializable
        val imageUrl = args.imageUrl
        val sol = "Sol ${args.sol}"
        val cameraText = "${args.cameraAbrr} - ${args.cameraFull}"
        val earthDate = "Data: ${args.earthDate}"

        binding.tvInfoTitle.text = sol
        binding.tvInfoImgCamera.text = cameraText
        binding.tvInfoImgEarthDate.text = earthDate

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
        Picasso.get().load(imageUrl).fit().centerInside().into(detailImageView)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}