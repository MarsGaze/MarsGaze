package com.digitalhouse.marsgaze.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.databinding.FragmentOnboarding2Binding

class Onboarding2Fragment : Fragment() {
    private var _binding: FragmentOnboarding2Binding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOnboarding2Binding.inflate(inflater, container, false)

        binding.btnComecar.setOnClickListener{
            findNavController().navigate(R.id.action_onboarding2Fragment_to_loginFragment)
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}