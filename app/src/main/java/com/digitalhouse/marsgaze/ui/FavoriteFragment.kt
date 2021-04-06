package com.digitalhouse.marsgaze.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.marsgaze.adapters.FavoriteAdapter
import com.digitalhouse.marsgaze.controllers.user.Session
import com.digitalhouse.marsgaze.database.AfterFavoriteAction
import com.digitalhouse.marsgaze.database.MarsGazeDB
import com.digitalhouse.marsgaze.databinding.FavoriteCardBinding
import com.digitalhouse.marsgaze.databinding.FragmentFavoritosBinding
import com.digitalhouse.marsgaze.models.favorite.FavoriteDetailAdapter
import com.digitalhouse.marsgaze.models.favorite.ImageDetailAdapter
import com.digitalhouse.marsgaze.viewmodels.session.SessionViewModelFactory
import com.digitalhouse.marsgaze.viewmodels.session.favorite.FavoriteViewModel

class FavoriteFragment : Fragment(), FavoriteAdapter.FavoriteAction {
    private var _binding: FragmentFavoritosBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var inDetailFavorite: ImageDetailAdapter? = null

    private var actionAfterDetail: ((Boolean) -> Unit)? = null

    private val adapter: FavoriteAdapter by lazy {
        binding.recyFav.adapter as FavoriteAdapter
    }

    private val viewModel by viewModels<FavoriteViewModel> {
        SessionViewModelFactory(
            Session.getInstance(
                MarsGazeDB.getDatabase(
                    requireContext()
                ),
                AfterFavoriteAction(
                    requireContext()
                )
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritosBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = FavoriteAdapter(arrayListOf(), this)
        binding.recyFav.setHasFixedSize(true)
        binding.recyFav.adapter = adapter
        binding.recyFav.layoutManager = LinearLayoutManager(context)

        viewModel.favorites.observe(viewLifecycleOwner) {
            adapter.list.clear()
            adapter.list.addAll(it)
            adapter.notifyDataSetChanged()
        }.run {
            viewModel.getAllFavorites()
        }


        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun favAction(
        favoriteTest: ImageDetailAdapter,
        position: Int,
        binding: FavoriteCardBinding
    ) {
        viewModel.favoriteAction(
            favoriteTest
        ) {
            if (it) {
                // Deixa o botão preenchido visivel para evitar uma animação extra
                val animate = binding.favoriteButtonFull.animate()
                animate.duration = 200
                animate.scaleY(1f).scaleX(1f)
                adapter.list[position] = FavoriteDetailAdapter.imageDetailAdapterToThis(
                    favoriteTest, true)

            } else {
                val animate = binding.favoriteButtonFull.animate()
                animate.duration = 200
                animate.scaleY(0f).scaleX(0f)
                adapter.list[position] = FavoriteDetailAdapter.imageDetailAdapterToThis(
                    favoriteTest, false)
            }
        }
    }

    override fun favDetail(favoriteTest: ImageDetailAdapter, binding: FavoriteCardBinding) {
        val action = FavoriteFragmentDirections.actionFavoriteFragmentToImageDetailFragment2(
            favoriteTest
        )
        findNavController().navigate(action)
        actionAfterDetail = {
            if (it) {
                // Deixa o botão preenchido visivel para evitar uma animação extra
                val animate = binding.favoriteButtonFull.animate()
                animate.duration = 200
                animate.scaleY(1f).scaleX(1f)
            } else {
                val animate = binding.favoriteButtonFull.animate()
                animate.duration = 200
                animate.scaleY(0f).scaleX(0f)
            }
        }
        inDetailFavorite = favoriteTest
    }
}