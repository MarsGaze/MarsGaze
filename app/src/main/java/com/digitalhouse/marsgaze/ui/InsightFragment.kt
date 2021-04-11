package com.digitalhouse.marsgaze.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.digitalhouse.marsgaze.adapters.InsightDataAdapter
import com.digitalhouse.marsgaze.adapters.InsightSolDateAdapter
import com.digitalhouse.marsgaze.adapters.InsightTitleMediaAdapter
import com.digitalhouse.marsgaze.controllers.service.InsightController
import com.digitalhouse.marsgaze.databinding.FragmentInsightBinding
import com.digitalhouse.marsgaze.viewmodels.InsightViewModel


class InsightFragment : Fragment() {

    private var _binding: FragmentInsightBinding? = null

    private val binding: FragmentInsightBinding
    get() = _binding!!

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

        binding.run {
            viewModel.insightResponse.observe(viewLifecycleOwner) {
                val adapterDate = insightVP.adapter as InsightSolDateAdapter
                val adapterMedia = insightDataInfo.adapter as InsightDataAdapter

                adapterDate.infoList = it
                insightVP.adapter = adapterDate

                adapterMedia.infoList = it
                insightDataInfo.adapter = adapterMedia

            }

            insightVP.addOnPageChangeListener (object: ViewPager.OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {}

                override fun onPageSelected(position: Int) {
                    val data = insightDataInfo.adapter as InsightDataAdapter
                    data.sol = position

                    insightDataBack.visibility = View.INVISIBLE
                    insightDataForward.visibility = View.VISIBLE

                    when (position) {
                        0 -> {
                            btnInsightBackDay.visibility = View.INVISIBLE
                            btnInsightForwardDay.visibility = View.VISIBLE
                        }
                        binding.insightVP.adapter!!.count - 1 -> {
                            btnInsightBackDay.visibility = View.VISIBLE
                            btnInsightForwardDay.visibility = View.INVISIBLE
                        }
                        else -> {
                            btnInsightBackDay.visibility = View.VISIBLE
                            btnInsightForwardDay.visibility = View.VISIBLE
                        }
                    }

                    insightDataTitle.currentItem = 0
                    insightDataInfo.adapter = data
                }

                override fun onPageScrollStateChanged(state: Int) {}
            } )

            insightDataInfo.addOnPageChangeListener (InsightListener(1))
            insightDataTitle.addOnPageChangeListener (InsightListener(0))

            insightVP.adapter = InsightSolDateAdapter()
            insightDataInfo.adapter = InsightDataAdapter()
            insightDataTitle.adapter = InsightTitleMediaAdapter()

            viewModel.getInsightInfo()

            btnInsightBackDay.setOnClickListener {
                val value = insightVP.currentItem - 1

                if (value != -1) {
                    insightVP.currentItem = value
                }
            }

            btnInsightForwardDay.setOnClickListener {
                val value = insightVP.currentItem + 1

                if (value != (insightVP.adapter as InsightSolDateAdapter).count) {
                    insightVP.currentItem = value
                }
            }

            insightDataForward.setOnClickListener {
                val value = insightDataTitle.currentItem + 1
                if (value != (insightDataTitle.adapter as InsightTitleMediaAdapter).count) {
                    insightDataTitle.currentItem = value
                }

                val value2 = insightDataInfo.currentItem + 1
                if (value2 != (insightDataInfo.adapter as InsightDataAdapter).count) {
                    insightDataInfo.currentItem = value2
                }
            }

            insightDataBack.setOnClickListener {
                val value = insightDataTitle.currentItem - 1
                if (value != -1) {
                    insightDataTitle.currentItem = value
                }

                val value2 = insightDataInfo.currentItem - 1
                if (value2 != -1) {
                    insightDataInfo.currentItem = value2
                }
            }
        }

    }

    inner class InsightListener(private val insightTypeInsight: Int) : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {}

        override fun onPageSelected(position: Int) {
            when (insightTypeInsight) {
                0 -> {
                    binding.insightDataInfo.currentItem = position
                }
                1 -> {
                    binding.insightDataTitle.currentItem = position
                }
            }
            when (position) {
                0 -> {
                    binding.insightDataBack.visibility = View.INVISIBLE
                    binding.insightDataForward.visibility = View.VISIBLE
                }
                else -> {
                    binding.insightDataBack.visibility = View.VISIBLE
                    binding.insightDataForward.visibility = View.INVISIBLE
                }
            }
        }

        override fun onPageScrollStateChanged(state: Int) {}
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}