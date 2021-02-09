package com.digitalhouse.marsgaze.ui.rovers

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.digitalhouse.marsgaze.databinding.FragmentCuriosityDetailBinding
import com.digitalhouse.marsgaze.databinding.FragmentSpOpDetailBinding

class SpOpDetailFragment : Fragment() {

    var _binding: FragmentSpOpDetailBinding? = null

    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSpOpDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.referenceLinkText.setOnClickListener {
            val intent = browserIntent()
            startActivity(intent)
        }

        binding.referenceText.setOnClickListener {
            val intent = browserIntent()
            startActivity(intent)
        }
    }

    private fun browserIntent(url: String = binding.referenceLinkText.text.toString()): Intent {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        return intent
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}