package com.digitalhouse.marsgaze.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.controllers.user.Session
import com.digitalhouse.marsgaze.database.MarsGazeDB
import com.digitalhouse.marsgaze.databinding.FragmentCadastroBinding
import com.digitalhouse.marsgaze.helper.OkAndErrorSnack
import com.digitalhouse.marsgaze.helper.SnackCreator
import com.digitalhouse.marsgaze.models.data.User
import com.digitalhouse.marsgaze.viewmodels.register.RegisterViewModel
import com.digitalhouse.marsgaze.viewmodels.register.RegisterViewModelFactory

class CadastroFragment : Fragment() {
    private var _binding: FragmentCadastroBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val session: Session by lazy {
        Session.getInstance(
            MarsGazeDB.getDatabase(
                requireContext()
            )
        )
    }

    // Inicializado somente após a criação da view
    private val viewModel: RegisterViewModel by viewModels {
        RegisterViewModelFactory(
            session
        )
    }

    private val snackCreator: SnackCreator by lazy {
        OkAndErrorSnack(
        requireView(),
        requireContext()
    ) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCadastroBinding.inflate(inflater, container, false)

        binding.btnBackLogin.setOnClickListener {
            findNavController().navigate(R.id.action_cadastroFragment_to_loginFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.registerStatus.observe(viewLifecycleOwner) {
            snackCreator.showSnack(it)
            if (it == Pair(true, R.string.userRegistered)) {
                findNavController().navigate(R.id.action_cadastroFragment_to_loginFragment)
            }
        }

        binding.btnCadastrar.setOnClickListener {
            val user = User(
                binding.tiEmail.editText!!.text.toString(),
                binding.tiNome.editText!!.text.toString(),
                binding.tiSenha.editText!!.text.toString(),
            )

            viewModel.registerUser(user)

            /*
            val intent = Intent(requireActivity(), NavigationActivity::class.java)
            startActivity(intent)
            requireActivity().finish() */
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}