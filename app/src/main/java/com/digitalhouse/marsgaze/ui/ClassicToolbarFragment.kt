package com.digitalhouse.marsgaze.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.digitalhouse.marsgaze.R
import kotlinx.android.synthetic.main.fragment_classic_toolbar.*

/*
 * Evita o retrabalho de configurar em cada fragment a toolbar para chamar o drawer layout.
 *
 * TODO: Talvez o ideal seja utilizar a activity para ficar com a toolbar e que ela tenha
 *  configurações para tratar os botões de ações entre outros.
 */
class ClassicToolbarFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_classic_toolbar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val drawer = requireActivity().findViewById<DrawerLayout>(R.id.drawer_layout)

            toolbar.setNavigationOnClickListener {
                drawer.open()
            }
        }

    }
}