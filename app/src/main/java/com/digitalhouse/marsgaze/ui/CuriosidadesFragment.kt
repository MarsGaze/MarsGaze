package com.digitalhouse.marsgaze.ui

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.digitalhouse.marsgaze.R
import kotlinx.android.synthetic.main.fragment_curiosidades.*
import com.digitalhouse.marsgaze.databinding.FragmentCuriosidadesBinding

class CuriosidadesFragment : Fragment() {

    private lateinit var binding: FragmentCuriosidadesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCuriosidadesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //PARTE HISTÓRICA

        //variaveis de controle para o card que contém o texto HISTORIA
        val cardHistoria = expandable_text_history
        val botaoHistoria = expand_button_history
        val textoHistoria = hidden_text_historia

        //ação para mostrar o texto Hitória
        setExpandable(cardHistoria, botaoHistoria, textoHistoria, null)

        //variaveis de controle para o card que contém o texto NOME
        val cardNome = expandable_text_name
        val botaoNome = expand_button_name
        val textoNome = hidden_text_name

        //ação para mostrar o texto NOME
        setExpandable(cardNome, botaoNome, textoNome, null)


        //variaveis de controle para o card que contém o texto CLIMA
        val cardClima = expandable_text_clima
        val botaoClima = expand_button_clima
        val textoClima = hidden_text_clima

        //ação para mostrar o texto CLIMA
        setExpandable(cardClima, botaoClima, textoClima, null)

        //variaveis de controle para o card que contém o texto SOLO
        val cardSolo = expandable_text_solo
        val botaoSolo = expand_button_solo
        val textoSolo = hidden_text_solo

        //ação para mostrar o texto SOLO
        setExpandable(cardSolo, botaoSolo, textoSolo, null)

        //variaveis de controle para o card que contém o texto SATELITES
        val cardSatelites = expandable_text_satelites
        val botaoSatelites = expand_button_satelites
        val textoSatelites = hidden_text_satelites

        //ação para mostrar o texto SATELITES
        setExpandable(cardSatelites, botaoSatelites, textoSatelites, null)

        //variaveis de controle para o card que contém o texto ATMOSFERA
        val cardAtmosfera = expandable_text_atmosfera
        val botaoAtmosfera = expand_button_atmosfera
        val textoAtmosfera = hidden_text_atmosfera

        //ação para mostrar o texto ATMOSFERA
        setExpandable(cardAtmosfera, botaoAtmosfera, textoAtmosfera, null)

        //PARTE DE CURIOSIDADES

        //variaveis de controle para o card que contém o texto CYDONIA
        val cardCydonia = expandable_text_cydonia
        val botaoCydonia = expand_button_cydonia
        val textoCydonia = hidden_text_cydonia
        val imgCydonia = img_cydonia

        //ação para mostrar o texto CYDONIA
        setExpandable(cardCydonia, botaoCydonia, textoCydonia, imgCydonia)

        //variaveis de controle para o card que contém o texto HAPPY FACE
        val cardHappyFace = expandable_text_happyFace
        val botaoHappyFace = expand_button_happyFace
        val textoHappyFace = hidden_text_happyFace
        val imgHappyFace = img_happyFace

        //ação para mostrar o texto HAPPY FACE
        setExpandable(cardHappyFace, botaoHappyFace, textoHappyFace, imgHappyFace)

        //variaveis de controle para o card que contém o texto BUTTERFLY
        val cardButterfly = expandable_text_butterfly
        val botaoButterfly = expand_button_butterfly
        val textoButterfly = hidden_text_butterfly
        val imgButterfly = img_butterfly

        //ação para mostrar o texto BUTTERFLY
        setExpandable(cardButterfly, botaoButterfly, textoButterfly, imgButterfly)

        //variaveis de controle para o card que contém o texto ANEL DE MARTE
        val cardAnelMarte = expandable_text_anelMarte
        val botaoAnelMarte = expand_button_anelMarte
        val textoAnelMarte = hidden_text_anelMarte

        //ação para mostrar o texto ANEL DE MARTE
        setExpandable(cardAnelMarte, botaoAnelMarte, textoAnelMarte, null)

        //variaveis de controle para o card que contém o texto CURIOSIDADES GEOGRAFICAS
        val cardCuriosidadesGeograficas = expandable_text_curiosidadesGeograficas
        val botaoCuriosidadesGeograficas = expand_button_curiosidadesGeograficas
        val textoCuriosidadesGeograficas = hidden_text_curiosidadesGeograficas

        //ação para mostrar o texto CURIOSIDADES GEOGRAFICAS
        setExpandable(cardCuriosidadesGeograficas, botaoCuriosidadesGeograficas, textoCuriosidadesGeograficas, null)
    }

    private fun setExpandable(expandableText: CardView,  expandButton: ImageView, hiddenText: TextView, hiddenImage: CardView?) {
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
                }
            }
        }
    }
}