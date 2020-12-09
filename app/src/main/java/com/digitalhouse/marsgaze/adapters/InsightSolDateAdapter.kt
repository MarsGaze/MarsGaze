package com.digitalhouse.marsgaze.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.digitalhouse.marsgaze.R
class InsightSolDateAdapter() : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean = `object` == view
    override fun getCount(): Int = 0

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(
            R.layout.insight_sol_page, container, false
        )

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}