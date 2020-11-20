package com.digitalhouse.marsgaze.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.adapters.InsightAdapter
import com.digitalhouse.marsgaze.adapters.InsightAdapterInsightData
import com.digitalhouse.marsgaze.adapters.InsightAdapterPageTitle
import kotlinx.android.synthetic.main.activity_insight.*

class InsightActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insight)

        insightVP.adapter = InsightAdapter()
        insightDataInfo.adapter = InsightAdapterInsightData()
        insightDataTitle.adapter = InsightAdapterPageTitle(insightDataInfo)

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