package com.digitalhouse.marsgaze.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.digitalhouse.marsgaze.R

/**
 * A simple [Fragment] subclass.
 * Use the [SpOpDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SpOpDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sp_op_detail, container, false)
    }

}