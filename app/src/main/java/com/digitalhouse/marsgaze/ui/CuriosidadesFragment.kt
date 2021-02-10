@file:Suppress("RedundantNullableReturnType", "RedundantNullableReturnType")

package com.digitalhouse.marsgaze.ui

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
import com.digitalhouse.marsgaze.databinding.FragmentCuriosidadesBinding

class CuriosidadesFragment : Fragment() {

    private var _binding: FragmentCuriosidadesBinding? = null

    private val binding: FragmentCuriosidadesBinding
    get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCuriosidadesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //PARTE HISTÓRICA

        binding.run {
            //variaveis de controle para o card que contém o texto HISTORIA
            val cardHistoria = expandableTextHistory
            val botaoHistoria = expandButtonHistory
            val textoHistoria = hiddenTextHistoria

            //ação para mostrar o texto Hitória
            setExpandable(cardHistoria, botaoHistoria, textoHistoria, null,
                          tituloHistoria)

            //variaveis de controle para o card que contém o texto NOME
            val cardNome = expandableTextName
            val botaoNome = expandButtonName
            val textoNome = hiddenTextName

            //ação para mostrar o texto NOME
            setExpandable(cardNome, botaoNome, textoNome, null,
                          binding.textoName)


            //variaveis de controle para o card que contém o texto CLIMA
            val cardClima = expandableTextClima
            val botaoClima = expandButtonClima
            val textoClima = hiddenTextClima

            //ação para mostrar o texto CLIMA
            setExpandable(cardClima, botaoClima, textoClima, null,
                          binding.textoClima)

            //variaveis de controle para o card que contém o texto SOLO
            val cardSolo = expandableTextSolo
            val botaoSolo = expandButtonSolo
            val textoSolo = hiddenTextSolo

            //ação para mostrar o texto SOLO
            setExpandable(cardSolo, botaoSolo, textoSolo, null,
                          binding.tituloSolo)

            //variaveis de controle para o card que contém o texto SATELITES
            val cardSatelites = expandableTextSatelites
            val botaoSatelites = expandButtonSatelites
            val textoSatelites = hiddenTextSatelites

            //ação para mostrar o texto SATELITES
            setExpandable(cardSatelites, botaoSatelites, textoSatelites, null,
                          binding.textoSatelites)

            //variaveis de controle para o card que contém o texto ATMOSFERA
            val cardAtmosfera = expandableTextAtmosfera
            val botaoAtmosfera = expandButtonAtmosfera
            val textoAtmosfera = hiddenTextAtmosfera

            //ação para mostrar o texto ATMOSFERA
            setExpandable(cardAtmosfera, botaoAtmosfera, textoAtmosfera, null,
                          binding.textoAtmosfera)

            //PARTE DE CURIOSIDADES

            //variaveis de controle para o card que contém o texto CYDONIA
            val cardCydonia = expandableTextCydonia
            val botaoCydonia = expandButtonCydonia
            val textoCydonia = hiddenTextCydonia
            val imgCydonia = imgCydonia

            //ação para mostrar o texto CYDONIA
            setExpandable(cardCydonia, botaoCydonia, textoCydonia, imgCydonia,
                          binding.textoCydonia)

            //variaveis de controle para o card que contém o texto HAPPY FACE
            val cardHappyFace = expandableTextHappyFace
            val botaoHappyFace = expandButtonHappyFace
            val textoHappyFace = hiddenTextHappyFace
            val imgHappyFace = imgHappyFace

            //ação para mostrar o texto HAPPY FACE
            setExpandable(cardHappyFace, botaoHappyFace, textoHappyFace, imgHappyFace,
                          binding.textoHappyFace)

            //variaveis de controle para o card que contém o texto BUTTERFLY
            val cardButterfly = expandableTextButterfly
            val botaoButterfly = expandButtonButterfly
            val textoButterfly = hiddenTextButterfly
            val imgButterfly = imgButterfly

            //ação para mostrar o texto BUTTERFLY
            setExpandable(cardButterfly, botaoButterfly, textoButterfly, imgButterfly,
                          binding.textoButterfly)

            //variaveis de controle para o card que contém o texto ANEL DE MARTE
            val cardAnelMarte = expandableTextAnelMarte
            val botaoAnelMarte = expandButtonAnelMarte
            val textoAnelMarte = hiddenTextAnelMarte

            //ação para mostrar o texto ANEL DE MARTE
            setExpandable(cardAnelMarte, botaoAnelMarte, textoAnelMarte, null,
                          binding.textoAnelMarte)

            //variaveis de controle para o card que contém o texto CURIOSIDADES GEOGRAFICAS
            val cardCuriosidadesGeograficas = expandableTextCuriosidadesGeograficas
            val botaoCuriosidadesGeograficas = expandButtonCuriosidadesGeograficas
            val textoCuriosidadesGeograficas = hiddenTextCuriosidadesGeograficas

            //ação para mostrar o texto CURIOSIDADES GEOGRAFICAS
            setExpandable(cardCuriosidadesGeograficas, botaoCuriosidadesGeograficas,
                textoCuriosidadesGeograficas, null, binding.textoCuriosidadesGeograficas)
        }
    }

    private fun setExpandable(expandableText: CardView,  expandButton: ImageView,
                              hiddenText: TextView, hiddenImage: CardView?,
                              titleText: TextView) {
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}