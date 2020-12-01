package com.digitalhouse.marsgaze.ui
// pew, pew, pew
import android.os.Bundle
import android.util.Log
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
import com.digitalhouse.marsgaze.adapters.RoversImageAdapter
import com.digitalhouse.marsgaze.databinding.FragmentRoversResultBinding
import com.digitalhouse.marsgaze.objects.RoverPhoto
import com.digitalhouse.marsgaze.objects.RoverResponse
import com.digitalhouse.marsgaze.services.MarsRoversPhotosService
import com.digitalhouse.marsgaze.utils.hideKeyboard
import com.digitalhouse.marsgaze.viewmodels.RoversResultViewModel
import java.util.*

class RoversResultFragment : Fragment(), RoversImageAdapter.OnItemClickListener {
    private val args: RoversResultFragmentArgs by navArgs()
    private val viewModel: RoversResultViewModel by viewModels() {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return RoversResultViewModel(MarsRoversPhotosService.create()) as T
            }
        }
    }

    private lateinit var binding: FragmentRoversResultBinding
    private lateinit var imageList: RoverResponse
    private lateinit var roverParameter: String
    private lateinit var solParameter: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRoversResultBinding.inflate(inflater, container, false)

        // Sets listener and behavior for "Filtrar" expandable menu and button
        setExpandableFilterMenuClickListener()
        setFilterButtonClickListener()

        imageList = RoverResponse(listOf())
        roverParameter = args.rover

        val imageAdapter: RoversImageAdapter = RoversImageAdapter(imageList, this)
        val recyclerView = binding.rvRoversResult
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = imageAdapter

        viewModel.photoList.observe(viewLifecycleOwner) {
            Log.i("RoversResultFragment", it.toString())
            imageList = it
            imageAdapter.adapterImageList = imageList
            recyclerView.adapter = imageAdapter

            if (imageList.photos.isEmpty()) {
                Toast.makeText(
                    context,
                    """Não foram encontradas imagens de ${roverParameter.capitalize(Locale.ROOT)} no Sol $solParameter.
                        |Alternativamente, buscamos as últimas imagens disponíveis.""".trimMargin(),
                    Toast.LENGTH_LONG
                ).show()

                viewModel.getLatestRoverPhotos(roverParameter)
            } else {
                solParameter = imageList.photos[0].sol.toString()
            }

            binding.inputSol.setText(solParameter)
        }

        if (!this::solParameter.isInitialized) {
            viewModel.getLatestRoverPhotos(roverParameter)
        }

        return binding.root
    }

    override fun onItemClick(position: Int) {
        val clickedItem: RoverPhoto = imageList.photos[position]
        findNavController(this).navigate(
            RoversResultFragmentDirections.actionRoversResultFragmentToImageDetailFragment2(
                clickedItem.imageUrl,
                solParameter,
                clickedItem.camera.abbrName,
                clickedItem.camera.fullName,
                clickedItem.earthDate
            )
        )
    }

    private fun setExpandableFilterMenuClickListener() {
        val expandableCard = binding.filterCard
        val expandButton = binding.expandButton
        val hiddenRadio = binding.radioGroup
        val hiddenTextInput = binding.filledTextField
        val filterButton = binding.buttonFilter

        expandButton.setOnClickListener {
            when (hiddenRadio.visibility) {
                View.VISIBLE -> {
                    hiddenRadio.visibility = View.GONE
                    hiddenTextInput.visibility = View.GONE
                    filterButton.visibility = View.GONE
                    TransitionManager.beginDelayedTransition(
                        expandableCard,
                        AutoTransition()
                    )
                    expandButton.setImageResource(R.drawable.ic_arrow_down_white)
                }

                else -> {
                    TransitionManager.beginDelayedTransition(
                        expandableCard,
                        AutoTransition()
                    )

                    when (roverParameter) {
                        "curiosity" -> hiddenRadio.check(R.id.radio_curiosity)
                        "spirit" -> hiddenRadio.check(R.id.radio_spirit)
                        "opportunity" -> hiddenRadio.check(R.id.radio_opportunity)
                    }

                    expandButton.setImageResource(R.drawable.ic_arrow_up_white)
                    hiddenRadio.visibility = View.VISIBLE
                    hiddenTextInput.visibility = View.VISIBLE
                    filterButton.visibility = View.VISIBLE
                }
            }
        }
    } // End setExpandableFilterClickListener()

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
            else viewModel.getRoverPhotos(roverParameter, solParameter.toInt())

            hideKeyboard()
            binding.expandButton.performClick()
        }
    }

}
