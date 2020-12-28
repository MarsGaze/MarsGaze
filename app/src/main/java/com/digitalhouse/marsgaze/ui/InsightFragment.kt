package com.digitalhouse.marsgaze.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.digitalhouse.marsgaze.adapters.InsightSolDateAdapter
import com.digitalhouse.marsgaze.adapters.InsightDataAdapter
import com.digitalhouse.marsgaze.adapters.InsightTitleMediaAdapter
import com.digitalhouse.marsgaze.controllers.service.InsightController
import com.digitalhouse.marsgaze.databinding.FragmentInsightBinding
import com.digitalhouse.marsgaze.viewmodels.InsightViewModel

class InsightFragment : Fragment() {
    private var _binding: FragmentInsightBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by viewModels<InsightViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return InsightViewModel(InsightController.getController()) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInsightBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.insightResponse.observe(viewLifecycleOwner) {
            val adapterDate = binding.insightVP.adapter as InsightSolDateAdapter
            val adapterMedia = binding.insightDataInfo.adapter as InsightDataAdapter

            adapterDate.infoList = it
            binding.insightVP.adapter = adapterDate

            adapterMedia.infoList = it
            binding.insightDataInfo.adapter = adapterMedia
        }

        binding.insightVP.addOnPageChangeListener (object: ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                return
            }

            override fun onPageSelected(position: Int) {
                val data = binding.insightDataInfo.adapter as InsightDataAdapter
                data.sol = position

                binding.insightDataTitle.currentItem = 0
                binding.insightDataInfo.adapter = data
            }

            override fun onPageScrollStateChanged(state: Int) {
                return
            }
        } )

        binding.insightDataInfo.addOnPageChangeListener (InsightListener(binding.insightDataTitle))
        binding.insightDataTitle.addOnPageChangeListener (InsightListener(binding.insightDataInfo))

        binding.insightVP.adapter = InsightSolDateAdapter()
        binding.insightDataInfo.adapter = InsightDataAdapter()
        binding.insightDataTitle.adapter = InsightTitleMediaAdapter(binding.insightDataInfo)

        viewModel.getInsightInfo()

        binding.btnInsightBackDay.setOnClickListener {
            val value = binding.insightVP.currentItem - 1
            if (value != -1) {
                binding.insightVP.currentItem = value
            }
        }

        binding.btnInsightForwardDay.setOnClickListener {
            val value = binding.insightVP.currentItem + 1
            if (value != (binding.insightVP.adapter as InsightSolDateAdapter).count) {
                binding.insightVP.currentItem = value
            }
        }

        binding.insightDataForward.setOnClickListener {
            val value = binding.insightDataTitle.currentItem + 1
            if (value != (binding.insightDataTitle.adapter as InsightTitleMediaAdapter).count) {
                binding.insightDataTitle.currentItem = value
            }

            val value2 = binding.insightDataInfo.currentItem + 1
            if (value2 != (binding.insightDataInfo.adapter as InsightDataAdapter).count) {
                binding.insightDataInfo.currentItem = value2
            }
        }

        binding.insightDataBack.setOnClickListener {
            val value = binding.insightDataTitle.currentItem - 1
            if (value != -1) {
                binding.insightDataTitle.currentItem = value
            }

            val value2 = binding.insightDataInfo.currentItem - 1
            if (value2 != -1) {
                binding.insightDataInfo.currentItem = value2
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    inner class InsightListener(private val insightReflect: ViewPager) : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            return
        }

        override fun onPageSelected(position: Int) {
            insightReflect.currentItem = position
        }

        override fun onPageScrollStateChanged(state: Int) {
            return
        }
    }
}