package com.digitalhouse.marsgaze.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.controllers.user.Session
import com.digitalhouse.marsgaze.database.AfterFavoriteAction
import com.digitalhouse.marsgaze.database.MarsGazeDB
import com.digitalhouse.marsgaze.databinding.FragmentLoginBinding
import com.digitalhouse.marsgaze.helper.OkAndErrorSnack
import com.digitalhouse.marsgaze.helper.SnackCreator
import com.digitalhouse.marsgaze.models.data.User
import com.digitalhouse.marsgaze.ui.NavigationActivity
import com.digitalhouse.marsgaze.viewmodels.login.LoginViewModel
import com.digitalhouse.marsgaze.viewmodels.login.LoginViewModelFactory

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val session: Session by lazy {
        Session.getInstance(
            MarsGazeDB.getDatabase(
                requireContext()
            ),
            AfterFavoriteAction(
                requireContext()
            )
        )
    }

    private val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(
            session
        )
    }

    private val snackCreator: SnackCreator by lazy {
        OkAndErrorSnack(
            requireView(),
            requireContext()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvCadastro.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_cadastroFragment)
        }

        binding.btnLogin.setOnClickListener {
            val email: String = binding.tfEmail.editText?.text.toString()
            val password: String = binding.tfSenha.editText?.text.toString()
            val user = User(email = email, password = password, name = "")

            viewModel.loginUser(user)
        }

        viewModel.loginStatus.observe(viewLifecycleOwner) {result ->
            if (!result.first) {
                snackCreator.showSnack(result)
            } else {
                // Já logamos o usuário aqui :P
                val intent = Intent(requireActivity(), NavigationActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}