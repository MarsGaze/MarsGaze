package com.digitalhouse.marsgaze.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {
    private var _binding: FragmentAboutBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAboutBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            twitterImage.setOnClickListener {
                val intent = twitterIntent()
                startActivity(intent)
            }

            emailImage.setOnClickListener {
                val intent = emailIntent()
                startActivity(intent)
            }

            setExpandable(
                expandableText, expandButtonPeople, aboutUs.root, null, teamText
            )
        }
    }

    private fun twitterIntent(user: String = "gaze_mars"): Intent {
        var intent: Intent? = null
        try {
            // get the Twitter app if possible
            requireContext().packageManager.getPackageInfo("com.twitter.android", 0)
            intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("twitter://user?screen_name=$user")
            )
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        } catch (e: Exception) {
            // no Twitter app, revert to browser
            intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/$user"))
        }

        return intent!!
    }

    private fun emailIntent(email: String = "marsgazeapp@gmail.com"): Intent {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:$email")

        return Intent.createChooser(intent, "Send Email")
    }

    private fun setExpandable(expandableText: CardView, expandButton: ImageView,
                              hiddenText: View, hiddenImage: CardView?,
                              titleText: TextView
    ) {
        expandButton.setOnClickListener {
            when (hiddenText.visibility) {
                View.VISIBLE -> {
                    if (hiddenImage != null) {
                        hiddenImage.visibility = View.GONE
                    }
                    hiddenText.visibility = View.GONE
                    androidx.transition.TransitionManager.beginDelayedTransition(
                        expandableText,
                        androidx.transition.AutoTransition()
                    )
                    expandButton.animate().rotationX(0F)

                    val col = ContextCompat.getColor(requireContext(), R.color.colorWhite)
                    titleText.setTextColor(col)
                }

                else -> {
                    if (hiddenImage != null) {
                        hiddenImage.visibility = View.VISIBLE
                    }

                    hiddenText.visibility = View.VISIBLE
                    androidx.transition.TransitionManager.beginDelayedTransition(
                        expandableText,
                        androidx.transition.AutoTransition()
                    )
                    expandButton.animate().rotationX(180F)

                    val col = ContextCompat.getColor(requireContext(), R.color.colorAccentDark)
                    titleText.setTextColor(col)
                }
            }
        }
    }
}