package com.digitalhouse.marsgaze.ui.rovers
// pew, pew, pew
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.Toast
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageDetailBinding.inflate(inflater, container, false)

        val photo = args.roverPhoto

        binding.tvInfoTitle.text = "Sol ${photo.sol}"
        binding.tvInfoImgCamera.text = "${photo.camera.abbrName} - ${photo.camera.fullName}"
        binding.tvInfoImgEarthDate.text = photo.earthDate

        setExpandableCardBehavior()

        val detailImageView: ImageView = binding.ivFullImage
        Picasso.get().load(photo.imageUrl).fit().centerInside().into(detailImageView)

        return binding.root
    }

    private fun setExpandableCardBehavior() {
        binding.ivFullImage.setOnClickListener {
            if (binding.groupInfo.visibility == View.VISIBLE) {
                binding.groupInfo.visibility = View.GONE
                TransitionManager.beginDelayedTransition(
                    binding.infoCard,
                    AutoTransition()
                )
                binding.expandButton.animate().rotationX(180F)
            }
        }

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
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}