package com.digitalhouse.marsgaze.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.models.insight.InsightInfo

class InsightDataAdapter(var infoList: ArrayList<InsightInfo> = ArrayList()) : PagerAdapter() {
    var sol: Int = 0
    override fun isViewFromObject(view: View, `object`: Any): Boolean = `object` == view
    override fun getCount(): Int = 2

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(
            R.layout.insight_sol_info, container, false
        )

        if (infoList.isEmpty()) {
            container.addView(view)
            return view
        }

        val obj = infoList[sol]

        val media = view.findViewById<TextView>(R.id.tv_media)
        val tvValorMinima = view.findViewById<TextView>(R.id.tv_valorMinima)
        val tvValorMaxima = view.findViewById<TextView>(R.id.tv_valorMaxima)
        val tvAmostras = view.findViewById<TextView>(R.id.tv_amostras)


        when(position) {
            0 -> {
                val res: (String) -> String =  {
                    media!!.resources.getString(R.string.insightTemp, it)
                }

                media.text = res(obj.AT.av)
                tvValorMinima.text = res(obj.AT.mn)
                tvValorMaxima.text = res(obj.AT.mx)
                val amostras = tvAmostras.resources.getString(
                    R.string.insightSampleSize, obj.AT.ct
                )
                tvAmostras.text = amostras
            }
            1 -> {
                val res: (String) -> String =  {
                    media!!.resources.getString(R.string.insightPress, it)
                }

                media.text = res(obj.PRE.av)
                tvValorMinima.text = res(obj.PRE.mn)
                tvValorMaxima.text = res(obj.PRE.mx)
                val amostras = tvAmostras.resources.getString(
                    R.string.insightSampleSize, obj.PRE.ct
                )
                tvAmostras.text = amostras
            }
        }

        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}