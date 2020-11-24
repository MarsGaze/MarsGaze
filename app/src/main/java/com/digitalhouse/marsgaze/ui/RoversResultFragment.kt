    package com.digitalhouse.marsgaze.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.InputQueue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.digitalhouse.marsgaze.R
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class RoversResultFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var imageAdapter: RoversImageAdapter
    private lateinit var imageList: ArrayList<RoversImageItem>
    private lateinit var requestQueue: RequestQueue

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_rovers_result, container, false)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        imageList = ArrayList()
        requestQueue = Volley.newRequestQueue(context)
        parseJson()

        Handler(Looper.getMainLooper()).postDelayed({

        }, 5000)


        // Setting expandable text click listener and behavior
        val expandableCard = view.findViewById<CardView>(R.id.filter_card)
        val expandButton = view.findViewById<ImageView>(R.id.expand_button)
        val hiddenRadio = view.findViewById<RadioGroup>(R.id.radioGroup)
        val hiddenTextInput = view.findViewById<TextInputLayout>(R.id.filledTextField)

        expandButton.setOnClickListener {
            when (hiddenRadio.visibility) {
                View.VISIBLE -> {
                    hiddenRadio.visibility = View.GONE
                    hiddenTextInput.visibility = View.GONE
                    TransitionManager.beginDelayedTransition(
                        expandableCard,
                        AutoTransition()
                    )
                    expandButton.setImageResource(R.drawable.ic_arrow_down_white)
                }

                else -> {
                    TransitionManager.beginDelayedTransition(
                        expandableCard,
                        AutoTransition()
                    )
                    expandButton.setImageResource(R.drawable.ic_arrow_up_white)
                    hiddenRadio.visibility = View.VISIBLE
                    hiddenTextInput.visibility = View.VISIBLE
                }
            }
        } // End expand button click listener


        return view
    }

    private fun parseJson(context: Context = requireContext()) {

        val url =
            "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=200&page=1&api_key=aYqv4pRlxcPJ2jcv0E26dh8c1VFgF5FDIRKnMbwg"
        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                try {
                    val jsonArray: JSONArray = response.getJSONArray("photos")

                    for (i in 0 until jsonArray.length()) {
                        val photo = jsonArray.getJSONObject(i)
                        val url: String = photo.getString("img_src").replace("http:", "https:")
//                        Log.i("url:", url)
                        imageList.add(RoversImageItem(url))
//                        Log.i("size", "${imageList.size}")
                    }

                    imageAdapter = RoversImageAdapter(context, imageList)
                    recyclerView.adapter = imageAdapter

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }, Response.ErrorListener { error -> error.printStackTrace() })

        requestQueue.add(request)
    }

}