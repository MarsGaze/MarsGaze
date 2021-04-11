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
            convertMonth(splitUTC[1])
        )
        insightDate.text = lastUTC
        val sol = insightMarsDay.resources.getString(
            R.string.solDay, obj.sol
        )
        insightMarsDay.text = sol
        insightSeason.text = obj.season

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    private fun convertMonth(num: String): String {
        return when (num) {
            "01" -> "Janeiro"
            "02" -> "Fevereiro"
            "03" -> "Março"
            "04" -> "Abril"
            "05" -> "Maio"
            "06" -> "Junho"
            "07" -> "Julho"
            "08" -> "Agosto"
            "09" -> "Setembro"
            "10" -> "Outubro"
            "11" -> "Novembro"
            else -> "Dezembro"
        }
    }
}