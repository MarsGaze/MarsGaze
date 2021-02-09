package com.digitalhouse.marsgaze.ui.rovers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.adapters.RoversResultAdapter
import com.digitalhouse.marsgaze.databinding.FragmentRoversResultBinding
import com.digitalhouse.marsgaze.models.rovers.RoverPhoto
import com.digitalhouse.marsgaze.services.MarsRoversPhotosService
import com.digitalhouse.marsgaze.utils.hideKeyboard
import com.digitalhouse.marsgaze.viewmodels.RoversResultViewModel

class RoversResultFragment : Fragment(), RoversResultAdapter.OnItemClickListener {
    private val args: RoversResultFragmentArgs by navArgs()

    private val viewModel: RoversResultViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return RoversResultViewModel(MarsRoversPhotosService.create()) as T
            }
        }
    }

    private var _binding: FragmentRoversResultBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var resultAdapter: RoversResultAdapter
    private lateinit var roverParameter: String
    private lateinit var solParameter: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoversResultBinding.inflate(inflater, container, false)

        // Sets listener and behavior for "Filtrar" expandable menu and button
        setExpandableFilterMenuClickListener()
        setFilterButtonClickListener()

        // Prevents overriding roverParameter when navigating back to this fragment
        if (!this::roverParameter.isInitialized) {
            roverParameter = args.rover
        }

        resultAdapter = RoversResultAdapter(this)
        val recyclerView = binding.rvRoversResult
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = resultAdapter

        // TODO: Check best practices
        viewModel.photoList.observe(viewLifecycleOwner) {
            resultAdapter.roverResponse = it
            resultAdapter.notifyDataSetChanged()
            // recyclerView.adapter = resultAdapter
            solParameter = resultAdapter.roverResponse.photos[0].sol
            binding.inputSol.setText(solParameter)
        }

        viewModel.message.observe(viewLifecycleOwner) { it ->
            it.getContentIfNotHandled()?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }

        // Self-explanatory
        if (!this::solParameter.isInitialized) {
            viewModel.getLatestRoverPhotos(roverParameter)
        }

        return binding.root
    }

    /**
     * Sets click behavior for photo items.
     * This function is used by the adapter.
     *
     */
    override fun onItemClick(position: Int) {
        val clickedItem: RoverPhoto = resultAdapter.roverResponse.photos[position]
        findNavController(this).navigate(
            RoversResultFragmentDirections.actionRoversResultFragmentToImageDetailFragment22(
                clickedItem
            )
        )
    }

    // Shrinks and expands filter menu
    private fun setExpandableFilterMenuClickListener() {

        // TODO: Retry grouping views
        val expandableCard = binding.filterCard
        val expandButton = binding.expandButton
        val cardContents = binding.filterCardContents
        val radioGroup = binding.radioGroup
        // val hiddenTextInput = binding.filledTextField
        // val filterButton = binding.buttonFilter

        expandButton.setOnClickListener {
            when (cardContents.visibility) {
                View.VISIBLE -> {
                    cardContents.visibility = View.GONE
                    TransitionManager.beginDelayedTransition(
                        expandableCard,
                        AutoTransition()
                    )
                    expandButton.animate().rotationX(0F)
                }

                else -> {
                    TransitionManager.beginDelayedTransition(
                        expandableCard,
                        AutoTransition()
                    )

                    /**
                     * Keeps the correct radio checked.
                     * TODO: There might be a better way to do this.
                     */
                    when (roverParameter) {
                        "curiosity" -> radioGroup.check(R.id.radio_curiosity)
                        "spirit" -> radioGroup.check(R.id.radio_spirit)
                        "opportunity" -> radioGroup.check(R.id.radio_opportunity)
                    }

                    expandButton.animate().rotationX(180F)
                    cardContents.visibility = View.VISIBLE
                }
            }
        }
    } // End setExpandableFilterClickListener()

    // Performs a new API request based on rover and sol parameters when "Filtrar" is clicked
    private fun setFilterButtonClickListener() {
        binding.buttonFilter.setOnClickListener {

            solParameter = binding.inputSol.text.toString()
            roverParameter = when (binding.radioGroup.checkedRadioButtonId) {
                R.id.radio_spirit -> "spirit"
                R.id.radio_opportunity -> "opportunity"
                R.id.radio_curiosity -> "curiosity"
                else -> roverParameter
            }

            if (solParameter.isBlank()) viewModel.getLatestRoverPhotos(roverParameter)
            else viewModel.getRoverPhotos(roverParameter, solParameter)

            // From utils/ContextExtensions
            hideKeyboard()
            binding.expandButton.performClick()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
