package com.digitalhouse.marsgaze.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.adapters.InsightSolDateAdapter
import com.digitalhouse.marsgaze.adapters.InsightDataAdapter
import com.digitalhouse.marsgaze.adapters.InsightTitleMediaAdapter
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

        insightVP.adapter = InsightSolDateAdapter()

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

        insightDataInfo.adapter = InsightDataAdapter()
        insightDataTitle.adapter = InsightTitleMediaAdapter(insightDataInfo)
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