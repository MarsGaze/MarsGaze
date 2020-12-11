package com.digitalhouse.marsgaze.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.digitalhouse.marsgaze.R
import kotlinx.android.synthetic.main.navigation_drawer.*

class NavigationActivity : AppCompatActivity() {
    // Controla em qual parte estamos para evitar uma chamada redundante.
    private lateinit var falseCall: String

    private lateinit var defaultPage: String
    // Controla quantas páginas estão abertas
    private var pages = 0;
    // Ajuda na verificação do controle de páginas, evitando falsas adições.
    private var withToolbarPage = true

    lateinit var navControl: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        defaultPage = getString(R.string.navigationItemStartPage)
        falseCall = defaultPage

        setContentView(R.layout.navigation_drawer)
    }

    override fun onStart() {
        super.onStart()

        navControl = findNavController(R.id.nav_host_fragment)

        navControl.addOnDestinationChangedListener() { _, _, _ ->
            // Toda iteração passa por aqui. Então quando voltamos o stack passamos aqui novamente
            // logo deduzimos duas vezes dos números de páginas, uma para entrar e outra do
            // aparecimento de outra tela.
            ++pages
            if (pages > 2) {
                withToolbarPage = false
            }
        }

        navControl = findNavController(R.id.nav_host_fragment)
        nav_view.setNavigationItemSelectedListener {
            // Vamos comparar pelo titulo que utiliza o recurso de string
            // para evitar problemas caso haja mudança entre as posições dos elementos do menu
            val title = it.title.toString()
            when (it.title) {
                resources.getString(R.string.navigationItemAboutMars) -> {
                    changePage(title, R.id.curiosidadesFragment)

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
                    changePage(title, R.id.roversFragment)
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
    fun changePage(title: String, res: Int) {
        if (falseCall != title) {
            falseCall = title
            when (title) {
                getString(R.string.navigationItemStartPage) ->  {
                    withToolbarPage = true
                    navControl.popBackStack()
                    pages -= 2
                }
                else -> {
                    withToolbarPage = true
                    if (pages == 1) {
                        navControl.navigate(res)
                    } else {
                        pages -= 2
                        navControl.popBackStack()
                        navControl.navigate(res)
                    }
                }
            }
        }

        // Mesmo com uma iteração repetida fechamos o drawer.
        drawer_layout.close()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        // Evita problemas na hora de voltar quando temos telas de outros fragmentos
        // Não devemos mexer na navegação das outras telas afinal.
        if (pages > 3 && !withToolbarPage) {
            pages -= 2
        } else {
            pages -= 2
            if (pages == 1) {
                falseCall = defaultPage
            }
        }
    }
}