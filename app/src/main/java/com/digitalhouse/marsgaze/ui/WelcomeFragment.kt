package com.digitalhouse.marsgaze.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.digitalhouse.marsgaze.R
import kotlinx.android.synthetic.main.fragment_welcome_page.*
import kotlinx.android.synthetic.main.navigation_drawer.*

class WelcomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_welcome_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        welcomeInsight.setOnClickListener {
            changePage(getString(R.string.navigationItemInsight), R.id.insightFragment)
        }

        welcomeMarte.setOnClickListener {
            changePage(getString(R.string.navigationItemAboutMars), R.id.curiosidadesFragment)
        }

        welcomeRover.setOnClickListener {
            changePage(getString(R.string.navigationItemRover), R.id.roversFragment)
        }
    }

    /**
     * Troca de página caso não esteja nela, fechando o menu do drawer e removendo do stack
     * a última página.
     */
    private fun changePage(title:String, res: Int) {
        val navActivity = requireContext() as NavigationActivity
        navActivity.changePage(title, res)
    }
}