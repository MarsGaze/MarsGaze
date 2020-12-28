package com.digitalhouse.marsgaze.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.models.insight.InsightInfo

class InsightSolDateAdapter(var infoList: ArrayList<InsightInfo> = ArrayList()) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean = `object` == view
    override fun getCount(): Int = infoList.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(
            R.layout.insight_sol_page, container, false
        )

        val obj = infoList[position]
        val insightDate = view.findViewById<TextView>(R.id.insightDate)
        val insightMarsDay = view.findViewById<TextView>(R.id.insightMarsDay)
        val insightSeason = view.findViewById<TextView>(R.id.insightSeason)



        val splitUTC = obj.lastUTC.split("-")
        val lastUTC = insightDate.resources.getString(
            R.string.insightSelectDate,
            splitUTC[2],
            splitUTC[0]
        )
        insightDate.text = lastUTC
        val sol = insightMarsDay.resources.getString(
            R.string.insightSolMars, obj.sol
        )
        insightMarsDay.text = sol
        insightSeason.text = obj.season

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}