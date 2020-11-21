package com.digitalhouse.marsgaze.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import com.digitalhouse.marsgaze.R


class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Precisamos customizar o tema desse fragmento para que os campos fiquem conforme o
        // desejado.
        val contextThemeWrapper: Context = ContextThemeWrapper(activity, R.style.MaterialTheme)
        val localInflater = inflater.cloneInContext(contextThemeWrapper)

        return localInflater.inflate(R.layout.fragment_profile, container, false)
    }
}