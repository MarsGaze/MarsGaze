package com.digitalhouse.marsgaze.ui

// pew, pew, pew
// pow?
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.core.content.FileProvider
import androidx.core.net.toFile
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
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

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
        binding.ivShare.setOnClickListener {
            onShareItem(binding.ivFullImage)
        }

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

    // Can be triggered by a view event such as a button press
    private fun onShareItem(v: View?) {
        // Get access to bitmap image from view
        val ivImage = binding.ivFullImage as ImageView
        // Get access to the URI for the bitmap
        val bmpUri: Uri? = getLocalBitmapUri(ivImage)
        if (bmpUri != null) {
            // Construct a ShareIntent with link to image
            val shareIntent = Intent()
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            shareIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri)
            shareIntent.putExtra(Intent.EXTRA_TEXT, args.imageDetailAdapter.getTitle())
            shareIntent.type = "*/*"
            // Launch sharing dialog for image
            startActivity(Intent.createChooser(shareIntent, "Share Image"))
        } else {
            // ...sharing failed, handle error
            return
        }
    }

    // Returns the URI path to the Bitmap displayed in specified ImageView
    private fun getLocalBitmapUri(imageView: ImageView): Uri? {
        // Extract Bitmap from ImageView drawable
        val drawable = imageView.drawable
        var bmp: Bitmap? = null
        bmp = if (drawable is BitmapDrawable) {
            (imageView.drawable as BitmapDrawable).bitmap
        } else {
            return null
        }
        // Store image to default external storage directory
        var bmpUri: Uri? = null
        try {
            val file = File(
                requireActivity().cacheDir,
                "share_image_" + System.currentTimeMillis() + ".png"
            )
            val out = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out)
            out.close()
            bmpUri = FileProvider.getUriForFile(requireActivity(), "com.digitalhouse.marsgaze", file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bmpUri
    }

}