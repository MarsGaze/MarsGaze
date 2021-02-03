package com.digitalhouse.marsgaze.ui

// pew, pew, pew
// pow?
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.controllers.user.Session
import com.digitalhouse.marsgaze.database.AfterFavoriteAction
import com.digitalhouse.marsgaze.database.MarsGazeDB
import com.digitalhouse.marsgaze.databinding.FragmentImageDetailBinding
import com.digitalhouse.marsgaze.helper.OkAndErrorSnack
import com.digitalhouse.marsgaze.helper.SnackCreator
import com.digitalhouse.marsgaze.models.data.FavoriteType
import com.digitalhouse.marsgaze.viewmodels.session.SessionViewModelFactory
import com.digitalhouse.marsgaze.viewmodels.session.image.ImageDetailViewModel
import com.squareup.picasso.Picasso

class ImageDetailFragment : Fragment() {
    private val args: ImageDetailFragmentArgs by navArgs()
    private var _binding: FragmentImageDetailBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val snackCreator: SnackCreator by lazy {
        OkAndErrorSnack(
            requireView(),
            requireContext()
        )
    }

    private val viewModel by viewModels<ImageDetailViewModel> {
        SessionViewModelFactory (
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageDetailBinding.inflate(inflater, container, false)

        val adapter = args.imageDetailAdapter

        binding.tvInfoTitle.text = getString(R.string.solDay, adapter.getTitle())

        when(adapter.getType()){
            // Rovers tem data e informação da camera
            FavoriteType.ROVERS_IMAGE.ordinal -> {
                binding.tvInfoImgCamera.text = adapter.getDesc()
                binding.tvInfoImgEarthDate.text = adapter.getExtraInfo() ?: ""
            }
            // Partes do hubble tem somente a data
            FavoriteType.HUBBLE_IMAGE.ordinal -> {
                binding.tvInfoImgCamera.text = adapter.getExtraInfo() ?: ""
            }
        }

        setExpandableCardBehavior()

        val detailImageView: ImageView = binding.ivFullImage
        Picasso.get().load(adapter.getImg()).fit().centerInside().into(detailImageView)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isFavorited(args.imageDetailAdapter)

        viewModel.favoriteStatus.observe(viewLifecycleOwner) {
            // Ignore ok messages
            if (!it.first) {
                snackCreator.showSnack(it)
            }
        }

        binding.ivFavorite.setOnClickListener {
            viewModel.favoriteAction(
                args.imageDetailAdapter
            )
        }

        viewModel.isFavorite.observe(viewLifecycleOwner) {
            // Realiza a animação dos favoritos (somente avisado se a transação tiver sucedido)
            if (it) {
                // Deixa o botão preenchido visivel para evitar uma animação extra
                binding.ivFavoriteFull.visibility = View.VISIBLE
                val animate = binding.ivFavoriteFull.animate()
                animate.duration = 200
                animate.scaleY(1f).scaleX(1f)
            } else {
                val animate = binding.ivFavoriteFull.animate()
                animate.duration = 200
                animate.scaleY(0f).scaleX(0f)
            }
        }
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