package com.digitalhouse.marsgaze.ui.hubble

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.navArgs
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.digitalhouse.marsgaze.databinding.FragmentDetailHubbleBinding
import com.squareup.picasso.Picasso

class DetailHubbleFragment : Fragment() {
    private val args: DetailHubbleFragmentArgs by navArgs()
    private var _binding: FragmentDetailHubbleBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailHubbleBinding.inflate(inflater, container, false)

        val imgUrl = args.hubble.links[0].linkHref.replace("thumb", "large")
        val title = args.hubble.data[0].title
        val description = args.hubble.data[0].description
        val date = args.hubble.data[0].date_created

        binding.tvHubbleTitle.text = title
        binding.tvHubbleDate.text = date

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

        val ivDetail: ImageView = binding.ivHubbleFull
        Picasso.get().load(imgUrl).fit().centerInside().into(ivDetail)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}