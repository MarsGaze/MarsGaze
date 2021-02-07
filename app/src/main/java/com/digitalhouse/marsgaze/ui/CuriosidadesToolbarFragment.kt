package com.digitalhouse.marsgaze.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.digitalhouse.marsgaze.R

@Suppress("unused")
open class CuriosidadesToolbarFragment : ClassicToolbarFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.curiosiridades_toolbar, container, false)
    }
}