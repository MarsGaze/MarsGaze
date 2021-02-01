package com.digitalhouse.marsgaze.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.controllers.user.Session
import com.digitalhouse.marsgaze.database.AfterFavoriteAction
import com.digitalhouse.marsgaze.database.MarsGazeDB
import com.digitalhouse.marsgaze.databinding.FragmentWelcomePageBinding
import com.google.firebase.auth.FirebaseAuth

class WelcomeFragment : Fragment() {
    private var _binding: FragmentWelcomePageBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomePageBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.welcomeHubble.setOnClickListener {
            changePage(getString(R.string.navigationItemHubble), R.id.hubbleFragment)
        }

        binding.welcomeInsight.setOnClickListener {
            changePage(getString(R.string.navigationItemInsight), R.id.insightFragment)
        }

        binding.welcomeMarte.setOnClickListener {
            changePage(getString(R.string.navigationItemAboutMars), R.id.curiosidadesFragment)
        }

        binding.welcomeRover.setOnClickListener {
            changePage(getString(R.string.navigationItemRover), R.id.roversFragment)
        }

        // Define o nome do usuário na tela principal
        defineWelcomeUser(
            Session.getInstance(
                MarsGazeDB.getDatabase(
                    requireContext()
                ),
                AfterFavoriteAction(requireContext())
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    /**
     * Troca de página caso não esteja nela, fechando o menu do drawer e removendo do stack
     * a última página.
     */
    private fun changePage(title:String, res: Int) {
        val navActivity = requireContext() as NavigationActivity
        navActivity.changePage(title, res)
    }

    private fun defineWelcomeUser(session: Session) {
        if (session.isLogged()) {
            binding.tvWelcome.text = getString(R.string.welcomeUser, session.user().name.split(" ")[0])
        } else {
            binding.tvWelcome.text = getString(R.string.welcomeUser, getString(
                R.string.anonUserName)
            )
        }

    }


}