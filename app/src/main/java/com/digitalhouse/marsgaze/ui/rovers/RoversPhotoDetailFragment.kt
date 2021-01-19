package com.digitalhouse.marsgaze.ui.rovers
// pew, pew, pew
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.digitalhouse.marsgaze.controllers.user.Session
import com.digitalhouse.marsgaze.database.AfterFavoriteAction
import com.digitalhouse.marsgaze.database.MarsGazeDB
import com.digitalhouse.marsgaze.databinding.FragmentImageDetailBinding
import com.digitalhouse.marsgaze.viewmodels.rover_image.RoverImageViewModel
import com.digitalhouse.marsgaze.viewmodels.rover_image.RoverImageViewModelFactory
import com.squareup.picasso.Picasso

// TODO: Change layout name
class RoversPhotoDetailFragment : Fragment() {
    private val args: RoversPhotoDetailFragmentArgs by navArgs()
    private var _binding: FragmentImageDetailBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by viewModels<RoverImageViewModel> {
        RoverImageViewModelFactory (
            Session.getInstance(
                MarsGazeDB.getDatabase(
                    requireContext()
                ),
                AfterFavoriteAction(
                    requireContext()
                )
            )
        )
    }

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

        binding.ivFavorite.setOnClickListener {

        }
        val detailImageView: ImageView = binding.ivFullImage
        Picasso.get().load(photo.imageUrl).fit().centerInside().into(detailImageView)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}