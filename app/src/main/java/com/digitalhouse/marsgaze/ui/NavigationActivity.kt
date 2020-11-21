package com.digitalhouse.marsgaze.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.digitalhouse.marsgaze.R
import kotlinx.android.synthetic.main.navigation_drawer.*

class NavigationActivity : AppCompatActivity() {
    // Controla em qual parte estamos para evitar uma chamada redundante.
    var falseCall = ""

    lateinit var navControl: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Colocar qual é a false call inicial.

        setContentView(R.layout.navigation_drawer)
    }

    override fun onStart() {
        super.onStart()

        navControl = findNavController(R.id.nav_host_fragment)
        nav_view.setNavigationItemSelectedListener {
            // Vamos comparar pelo titulo que utiliza o recurso de string
            // para evitar problemas caso haja mudança entre as posições dos elementos do menu
            val title = it.title.toString()
            when (it.title) {
                resources.getString(R.string.navigationItemAboutMars) -> {
                    true
                }
                resources.getString(R.string.navigationItemFavorite) -> {
                    changePage(title, R.id.favoriteFragment)

                    true
                }

                resources.getString(R.string.navigationItemInsight) -> {
                    changePage(title, R.id.insightFragment)

                    true
                }

                resources.getString(R.string.navigationItemProfile) -> {
                    changePage(title, R.id.profileFragment)
                    true
                }

                resources.getString(R.string.navigationItemRover) -> {
                    false
                }

                resources.getString(R.string.navigationItemStartPage) -> {
                    changePage(title, R.id.welcomeFragment)
                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    /**
     * Troca de página caso não esteja nela, fechando o menu do drawer e removendo do stack
     * a última página.
     */
    private fun changePage(title: String, res: Int) {
        if (falseCall != title) {
            falseCall = title
            navControl.popBackStack()
            navControl.navigate(res)
        }

        drawer_layout.close()
    }
}