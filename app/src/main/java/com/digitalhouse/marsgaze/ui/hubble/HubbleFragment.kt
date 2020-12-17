package com.digitalhouse.marsgaze.ui.hubble

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.digitalhouse.marsgaze.adapters.HubbleAdapter
import com.digitalhouse.marsgaze.databinding.FragmentHubbleBinding
import com.digitalhouse.marsgaze.models.hubble.HubbleResponse
import com.digitalhouse.marsgaze.models.hubble.Item
import com.digitalhouse.marsgaze.models.hubble.PhotoCollection
import com.digitalhouse.marsgaze.services.HubbleService
import com.digitalhouse.marsgaze.viewmodels.HubbleViewModel

class HubbleFragment : Fragment(), HubbleAdapter.OnItemClickListener {
    private val viewModel: HubbleViewModel by viewModels() {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return HubbleViewModel(HubbleService.create()) as T
            }
        }
    }

    private var _binding: FragmentHubbleBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    private lateinit var hubbleImageList: HubbleResponse

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHubbleBinding.inflate(inflater, container, false)

        // Condition prevents clearing hubbleImageList.collections.items
        if (!this::hubbleImageList.isInitialized) {
            hubbleImageList = HubbleResponse(PhotoCollection(listOf()))
        }

        val hubbleAdapter = HubbleAdapter(hubbleImageList, this)
        val rvHubble = binding.rvHubble
        rvHubble.layoutManager = GridLayoutManager(context, 2)
        rvHubble.adapter = hubbleAdapter

        viewModel.hubbleImgList.observe(viewLifecycleOwner) {
            hubbleImageList = it
            hubbleAdapter.adapterHubbleList = hubbleImageList
            rvHubble.adapter = hubbleAdapter
        }

        // Condition prevents requesting when navigating back from DetailHubbleFragment
        if (hubbleImageList.collection.items.isEmpty()) {
            viewModel.getHubbleImg()
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onItemClick(position: Int) {
        val clickedItem: Item = hubbleImageList.collection.items[position]
        NavHostFragment.findNavController(this).navigate(
            HubbleFragmentDirections.actionHubbleFragment3ToDetailHubbleFragment2(
                clickedItem
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}