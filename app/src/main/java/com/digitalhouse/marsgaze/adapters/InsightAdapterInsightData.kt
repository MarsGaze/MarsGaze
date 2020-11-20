package com.digitalhouse.marsgaze.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.digitalhouse.marsgaze.R

class InsightAdapterInsightData : PagerAdapter() {
    override fun getCount(): Int = 2

    override fun isViewFromObject(view: View, `object`: Any): Boolean = `object` == view

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val info = LayoutInflater.from(container.context).inflate(
            R.layout.insight_sol_info, container, false
        )

        container.addView(info)

        return info
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}