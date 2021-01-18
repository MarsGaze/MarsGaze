package com.digitalhouse.marsgaze.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.digitalhouse.marsgaze.adapters.FavoriteAdapter
import com.digitalhouse.marsgaze.databinding.FragmentFavoritosBinding
import com.digitalhouse.marsgaze.models.data.Favorite

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoritosBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritosBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FavoriteAdapter(Favorite.generator())

        binding.recyFav.setHasFixedSize(true)
        binding.recyFav.adapter = adapter
        binding.recyFav.layoutManager = LinearLayoutManager(context)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}