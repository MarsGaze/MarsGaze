package com.digitalhouse.marsgaze.ui.hubble

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.adapters.HubbleAdapter
import com.digitalhouse.marsgaze.databinding.FragmentHubbleBinding
import com.digitalhouse.marsgaze.databinding.FragmentRoversResultBinding
import com.digitalhouse.marsgaze.models.hubble.HubbleResponse
import com.digitalhouse.marsgaze.models.hubble.Item
import com.digitalhouse.marsgaze.models.rovers.RoverPhoto
import com.digitalhouse.marsgaze.models.rovers.RoverResponse
import com.digitalhouse.marsgaze.services.HubbleService
import com.digitalhouse.marsgaze.services.MarsRoversPhotosService
import com.digitalhouse.marsgaze.ui.rovers.RoversResultFragmentArgs
import com.digitalhouse.marsgaze.ui.rovers.RoversResultFragmentDirections
import com.digitalhouse.marsgaze.viewmodels.HubbleViewModel
import com.digitalhouse.marsgaze.viewmodels.RoversResultViewModel
import java.util.*

class HubbleFragment : Fragment(), HubbleAdapter.OnItemClickListener {
    private val viewModel: HubbleViewModel by viewModels() {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return HubbleViewModel(HubbleService.create()) as T
            }
        }
    }

    private lateinit var binding: FragmentHubbleBinding // replaces kotlin synthetics
    private lateinit var hubbleImageList: HubbleResponse

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHubbleBinding.inflate(inflater, container, false)

        hubbleImageList = HubbleResponse(listOf())

        val hubbleAdapter = HubbleAdapter(hubbleImageList, this)
        val rvHubble = binding.rvHubble
        rvHubble.layoutManager = GridLayoutManager(context, 2)
        rvHubble.adapter = hubbleAdapter

        viewModel.hubbleImgList.observe(viewLifecycleOwner) {
            hubbleImageList = it
            hubbleAdapter.adapterHubbleList = hubbleImageList
            rvHubble.adapter = hubbleAdapter

        }

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onItemClick(position: Int) {
        val clickedItem: Item = hubbleImageList.itens[position]
        NavHostFragment.findNavController(this).navigate(
            HubbleFragmentDirections.actionHubbleFragment2ToDetailHubbleFragment(
                clickedItem
            )
        )
    }

}