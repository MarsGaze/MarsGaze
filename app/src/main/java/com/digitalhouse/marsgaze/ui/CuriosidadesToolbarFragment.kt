package com.digitalhouse.marsgaze.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.digitalhouse.marsgaze.R
import kotlinx.android.synthetic.main.fragment_classic_toolbar.*

open class CuriosidadesToolbarFragment : ClassicToolbarFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.curiosiridades_toolbar, container, false)
    }
}