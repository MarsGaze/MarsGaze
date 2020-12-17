package com.digitalhouse.marsgaze.adapters

import android.util.Log
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

        var msg = " aslkjdaksldhalskjdh"

        Log.i("\n\n\n\n\n AOISDASLDH", infoList.toString())
        Log.i("\n\n\n\n\n 5435465465454", msg)

        if (infoList.isEmpty()) {
            container.addView(view)
            return view
        }

        val obj = infoList[sol]

        val media = view.findViewById<TextView>(R.id.tv_media)
        val tv_valorMinima = view.findViewById<TextView>(R.id.tv_valorMinima)
        val tv_valorMaxima = view.findViewById<TextView>(R.id.tv_valorMaxima)
        val tv_amostras = view.findViewById<TextView>(R.id.tv_amostras)


        when(position) {
            0 -> {
                media.text = obj.AT.av
                tv_valorMinima.text = obj.AT.mn
                tv_valorMaxima.text = obj.AT.mx
                tv_amostras.text = obj.AT.ct
            }
            1 -> {
                media.text = obj.PRE.av
                tv_valorMinima.text = obj.PRE.mn
                tv_valorMaxima.text = obj.PRE.mx
                tv_amostras.text = obj.PRE.ct
            }
        }

        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}