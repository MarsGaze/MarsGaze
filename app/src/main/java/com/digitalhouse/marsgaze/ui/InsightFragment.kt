package com.digitalhouse.marsgaze.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.adapters.InsightSolDateAdapter
import com.digitalhouse.marsgaze.adapters.InsightDataAdapter
import com.digitalhouse.marsgaze.adapters.InsightTitleMediaAdapter
import com.digitalhouse.marsgaze.services.InsightService
import com.digitalhouse.marsgaze.viewmodels.InsightViewModel
import kotlinx.android.synthetic.main.fragment_insight.*

class InsightFragment : Fragment() {

    private val viewModel by viewModels<InsightViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return InsightViewModel(InsightService.create()) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_insight, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            ) {
                position
            }

            override fun onPageSelected(position: Int) {
                var data = insightDataInfo.adapter as InsightDataAdapter
                data.sol = position

                insightDataBack.visibility = View.INVISIBLE
                insightDataForward.visibility = View.VISIBLE

                insightDataTitle.currentItem = 0
                insightDataInfo.adapter = data
            }

            override fun onPageScrollStateChanged(state: Int) {
                state
            }
        } )

        insightDataInfo.addOnPageChangeListener (InsightListener(1))
        insightDataTitle.addOnPageChangeListener (InsightListener(0))

        insightVP.adapter = InsightSolDateAdapter()
        insightDataInfo.adapter = InsightDataAdapter()
        insightDataTitle.adapter = InsightTitleMediaAdapter(insightDataInfo)

        viewModel.getInsightInfo()

        btnInsightBackDay.setOnClickListener {
            val value = insightVP.currentItem - 1
            btnInsightForwardDay.visibility = View.VISIBLE


            if (value != -1) {
                insightVP.currentItem = value

                insightDataBack.visibility = View.INVISIBLE
                insightDataForward.visibility = View.VISIBLE

                if (insightVP.currentItem == 1) {
                    btnInsightBackDay.visibility = View.INVISIBLE
                    btnInsightForwardDay.visibility = View.VISIBLE
                }
            }
        }

        btnInsightForwardDay.setOnClickListener {
            val value = insightVP.currentItem + 1

            btnInsightBackDay.visibility = View.VISIBLE

            if (value != (insightVP.adapter as InsightSolDateAdapter).count) {
                insightVP.currentItem = value

                insightDataBack.visibility = View.INVISIBLE
                insightDataForward.visibility = View.VISIBLE

                val adapter = insightVP.adapter as InsightSolDateAdapter

                if(adapter.infoList.size -1 == value) {
                    btnInsightForwardDay.visibility = View.INVISIBLE
                }
            }
        }

        insightDataForward.setOnClickListener {
            insightDataBack.visibility = View.VISIBLE
            insightDataForward.visibility = View.INVISIBLE
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
            insightDataBack.visibility = View.INVISIBLE
            insightDataForward.visibility = View.VISIBLE
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

    inner class InsightListener(val insightTypeInsight: Int) : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            position
        }

        override fun onPageSelected(position: Int) {
            when (insightTypeInsight) {
                0 -> {
                    insightDataInfo.currentItem = position
                }
                1 -> {
                    insightDataTitle.currentItem = position
                }
            }
        }

        override fun onPageScrollStateChanged(state: Int) {
            state
        }
    }
}