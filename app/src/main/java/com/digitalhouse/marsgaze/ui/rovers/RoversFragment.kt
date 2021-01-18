package com.digitalhouse.marsgaze.ui.rovers
// pew, pew, pew
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.databinding.FragmentRoversBinding

class RoversFragment : Fragment() {
    private var _binding: FragmentRoversBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoversBinding.inflate(inflater, container, false)

        navController = findNavController(this)

        // Sets listeners for "SAIBA MAIS" buttons
        setInfoButtonsListeners()
        // Sets listeners for "IMAGENS" buttons
        setPhotosButtonsListeners()
        // Sets listener and behavior for "O que é um Rover?" expandable text
        setExpandableTextClickListener()

        return binding.root
    }

    private fun setInfoButtonsListeners() {

        // Curiosity "SAIBA MAIS"
        binding.infoButtonCuriosityCard.setOnClickListener {
            navController.navigate(R.id.action_roversFragment_to_curiosityDetailFragment)
        }

        // Spirit "SAIBA MAIS"
        binding.infoButtonSpCard.setOnClickListener {
            navController.navigate(R.id.action_roversFragment_to_spOpDetailFragment)
        }

        // Opportunity "SAIBA MAIS"
        binding.infoButtonOpCard.setOnClickListener {
            navController.navigate(R.id.action_roversFragment_to_spOpDetailFragment)
        }
    }

    private fun setPhotosButtonsListeners() {

        binding.photosButtonCuriosityCard.setOnClickListener {
            navController.navigate(
                RoversFragmentDirections.actionRoversFragmentToRoversResultFragment(
                    "curiosity"
                )
            )
        }

        binding.photosButtonSpCard.setOnClickListener {
            navController.navigate(
                RoversFragmentDirections.actionRoversFragmentToRoversResultFragment(
                    "spirit"
                )
            )
        }

        binding.photosButtonOpCard.setOnClickListener {
            navController.navigate(
                RoversFragmentDirections.actionRoversFragmentToRoversResultFragment(
                    "opportunity"
                )
            )
        }
    }

    private fun setExpandableTextClickListener() {
        val expandableText = binding.roverExpandableText
        val expandButton = binding.roverExpandButton
        val hiddenText = binding.hiddenText

        /**
         * Animated the arrow rotation instead of replacing with another drawable.
         * TODO: Delete drawable/ic_arrow_up_white
         *
         */
        expandButton.setOnClickListener {
            when (hiddenText.visibility) {
                View.VISIBLE -> {
                    hiddenText.visibility = View.GONE
                    TransitionManager.beginDelayedTransition(
                        expandableText,
                        AutoTransition()
                    )
                    expandButton.animate().rotationX(0F)
                }

                else -> {
                    TransitionManager.beginDelayedTransition(
                        expandableText,
                        AutoTransition()
                    )
                    expandButton.animate().rotationX(180F)
                    hiddenText.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
