package com.digitalhouse.marsgaze.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.digitalhouse.marsgaze.R

// TODO: Essa classe não deixa claro como as informações vão ser linkadas depois
class InsightTitleMediaAdapter(val info: ViewPager) : PagerAdapter() {
    override fun getCount(): Int = 2

    override fun isViewFromObject(view: View, `object`: Any): Boolean = `object` == view

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val title = LayoutInflater.from(container.context).inflate(
            R.layout.insight_title_media, container, false
        ) as TextView

        title.text = title.resources.getString(R.string.insightPressure)
        if (position == 0) {
            title.text = title.resources.getString(R.string.insightTemperature)
        }

        container.addView(title)

        return title
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}