package com.digitalhouse.marsgaze.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.databinding.InsightSolInfoBinding
import com.digitalhouse.marsgaze.models.insight.InsightInfo

class InsightDataAdapter(var infoList: ArrayList<InsightInfo> = ArrayList()) : PagerAdapter() {
    var sol: Int = 0
    private lateinit var view: View
    override fun isViewFromObject(view: View, `object`: Any): Boolean = `object` == view
    override fun getCount(): Int = 2

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        if (infoList.isEmpty()) {
            view = LayoutInflater.from(container.context).inflate(
                R.layout.insight_exception, container, false )
            return view
        }

        val obj = infoList[sol]

        when(position) {
            0 -> {
                if (obj.AT.av == "NO_DATA") {
                    view = LayoutInflater.from(container.context).inflate(
                        R.layout.insight_exception, container, false
                    )
                } else {
                    view = LayoutInflater.from(container.context).inflate(
                        R.layout.insight_sol_info, container, false
                    )
                    inflateDataLayout(view, position, obj)
                }
            }
            1 -> {
                if (obj.PRE.av == "NO_DATA") {
                    view = LayoutInflater.from(container.context).inflate(
                        R.layout.insight_exception, container, false
                    )
                } else {
                    view = LayoutInflater.from(container.context).inflate(
                        R.layout.insight_sol_info, container, false
                    )
                    inflateDataLayout(view, position, obj)
                }
            }
        }



        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    private fun inflateDataLayout(view: View, position: Int, obj: InsightInfo) {
        val media = view.findViewById<TextView>(R.id.tv_media)
        val binding = InsightSolInfoBinding.bind(view)

        binding.run {
            when(position) {
                0 -> {
                    media.text = view.resources.getString(R.string.insightTemp, obj.AT.av)
                    tvValorMinima.text = view.resources.getString(R.string.insightTemp, obj.AT.mn)
                    tvValorMaxima.text = view.resources.getString(R.string.insightTemp, obj.AT.mx)
                    tvAmostras.text = view.resources.getString(
                        R.string.insightSampleSize,
                        obj.AT.ct
                    )
                }
                1 -> {
                    media.text = view.resources.getString(R.string.insightPress, obj.PRE.av)
                    tvValorMinima.text = view.resources.getString(R.string.insightPress, obj.PRE.mn)
                    tvValorMaxima.text = view.resources.getString(R.string.insightPress, obj.PRE.mx)
                    tvAmostras.text = view.resources.getString(
                        R.string.insightSampleSize,
                        obj.PRE.ct
                    )
                }
            }
        }
    }
}