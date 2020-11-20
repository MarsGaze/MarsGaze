package com.digitalhouse.marsgaze.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.digitalhouse.marsgaze.R
import kotlinx.android.synthetic.main.fragment_rovers.*

class RoversFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_rovers, container, false)

        // Setting expandable text click listener and behavior
        val expandableText = view.findViewById<CardView>(R.id.rover_expandable_text)
        val expandButton = view.findViewById<ImageView>(R.id.rover_expand_button)
        val hiddenText = view.findViewById<TextView>(R.id.hidden_text)

        expandButton.setOnClickListener {
            when (hiddenText.visibility) {
                View.VISIBLE -> {
                    hiddenText.visibility = View.GONE
                    TransitionManager.beginDelayedTransition(
                        expandableText,
                        AutoTransition()
                    )
                    expandButton.setImageResource(R.drawable.ic_arrow_down_white)
                }

                else -> {
                    TransitionManager.beginDelayedTransition(
                        expandableText,
                        AutoTransition()
                    )
                    expandButton.setImageResource(R.drawable.ic_arrow_up_white)
                    hiddenText.visibility = View.VISIBLE
                }
            }
        } // End expand button click listener

        // Setting info button click listeners
        view.findViewById<Button>(R.id.info_button_curiosity_card).setOnClickListener { View ->
            view.findNavController().navigate(R.id.action_roversFragment_to_curiosityDetailFragment)
        }

        view.findViewById<Button>(R.id.info_button_sp_card).setOnClickListener { View ->
            view.findNavController().navigate(R.id.action_roversFragment_to_spOpDetailFragment)
        }

        view.findViewById<Button>(R.id.info_button_op_card).setOnClickListener { View ->
            view.findNavController().navigate(R.id.action_roversFragment_to_spOpDetailFragment)
        }

        view.findViewById<Button>(R.id.right_button_curiosity_card).setOnClickListener { View ->
            view.findNavController().navigate(R.id.action_roversFragment_to_roversResultFragment)
        }

        return view
    }

}
