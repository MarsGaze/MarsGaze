package com.digitalhouse.marsgaze.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.digitalhouse.marsgaze.R
import kotlinx.android.synthetic.main.fragment_onboarding.view.*
import kotlinx.android.synthetic.main.fragment_onboarding.view.btnNextOb
import kotlinx.android.synthetic.main.fragment_onboarding2.view.*

class Onboarding2Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_onboarding2, container, false)

        view.btnComecar.setOnClickListener{
            findNavController().navigate(R.id.action_onboarding2Fragment_to_loginFragment)
        }

        return view
    }

}