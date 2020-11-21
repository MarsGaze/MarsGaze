package com.digitalhouse.marsgaze.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.adapters.InsightAdapter
import com.digitalhouse.marsgaze.adapters.InsightAdapterInsightData
import com.digitalhouse.marsgaze.adapters.InsightAdapterPageTitle
import kotlinx.android.synthetic.main.fragment_insight.*

class InsightFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_insight, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        insightVP.adapter = InsightAdapter()

        btnInsightBackDay.setOnClickListener {
            val value = insightVP.currentItem - 1
            if (value != -1) {
                insightVP.currentItem = value
            }
        }

        btnInsightForwardDay.setOnClickListener {
            val value = insightVP.currentItem + 1
            if (value != (insightVP.adapter as InsightAdapter).count) {
                insightVP.currentItem = value
            }
        }

        insightDataInfo.adapter = InsightAdapterInsightData()
        insightDataTitle.adapter = InsightAdapterPageTitle(insightDataInfo)
        insightDataForward.setOnClickListener {
            val value = insightDataTitle.currentItem + 1
            if (value != (insightDataTitle.adapter as InsightAdapterPageTitle).count) {
                insightDataTitle.currentItem = value
            }

            val value2 = insightDataInfo.currentItem + 1
            if (value2 != (insightDataInfo.adapter as InsightAdapterInsightData).count) {
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