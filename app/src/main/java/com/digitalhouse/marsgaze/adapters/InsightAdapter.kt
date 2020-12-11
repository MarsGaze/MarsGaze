package com.digitalhouse.marsgaze.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.digitalhouse.marsgaze.R

class InsightAdapter : PagerAdapter() {
    override fun getCount(): Int = 5

    override fun isViewFromObject(view: View, `object`: Any): Boolean = `object` == view

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