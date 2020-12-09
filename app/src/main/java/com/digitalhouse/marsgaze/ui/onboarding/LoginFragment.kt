package com.digitalhouse.marsgaze.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.ui.NavigationActivity
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvCadastro.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_cadastroFragment)
        }
        btnLogin.setOnClickListener {
            val intent = Intent(requireActivity(), NavigationActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

}