package com.digitalhouse.marsgaze.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.digitalhouse.marsgaze.R
import kotlinx.android.synthetic.main.fragment_cadastro.*
import kotlinx.android.synthetic.main.fragment_cadastro.view.*
import kotlinx.android.synthetic.main.fragment_login.*

class CadastroFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater!!.inflate(R.layout.fragment_cadastro, container, false)

        view.btnBackLogin.setOnClickListener {
            findNavController().navigate(R.id.action_cadastroFragment_to_loginFragment)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnCadastrar.setOnClickListener {
            val intent = Intent(requireActivity(), NavigationActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

}