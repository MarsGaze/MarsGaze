package com.digitalhouse.marsgaze.ui.rovers
// pew, pew, pew

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.digitalhouse.marsgaze.databinding.FragmentImageDetailBinding
import com.squareup.picasso.Picasso
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

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

        binding.ivShare.setOnClickListener {
            onShareItem(binding.ivFullImage)
        }

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
            shareIntent.type = "image/*"
            // Launch sharing dialog for image
            startActivityForResult(Intent.createChooser(shareIntent, "Share Image"), 1000)
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
            // Use methods on Context to access package-specific directories on external storage.
            // This way, you don't need to request external read/write permission.
            // See https://youtu.be/5xVh-7ywKpE?t=25m25s
            val file = File(
                requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "share_image_" + System.currentTimeMillis() + ".png"
            )
            val out = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out)
            out.close()
            // **Warning:** This will fail for API >= 24, use a FileProvider as shown below instead.
            bmpUri = FileProvider.getUriForFile(requireActivity(), "com.digitalhouse.marsgaze", file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bmpUri
    }

    // Method when launching drawable within Glide
    fun getBitmapFromDrawable(bmp: Bitmap): Uri? {

        // Store image to default external storage directory
        var bmpUri: Uri? = null
        try {
            // Use methods on Context to access package-specific directories on external storage.
            // This way, you don't need to request external read/write permission.
            // See https://youtu.be/5xVh-7ywKpE?t=25m25s
            val file = File(
                requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "share_image_" + System.currentTimeMillis() + ".png"
            )
            val out = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out)
            out.close()

            // wrap File object into a content provider. NOTE: authority here should match authority in manifest declaration
            bmpUri = FileProvider.getUriForFile(
                requireContext(),
                "com.digitalhouse.marsgaze",
                file
            ) // use this version for API >= 24

            // **Note:** For API < 24, you may use bmpUri = Uri.fromFile(file);
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bmpUri
    }
}