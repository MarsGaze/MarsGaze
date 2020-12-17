package com.digitalhouse.marsgaze.ui

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.databinding.FragmentCuriosidadesBinding

class CuriosidadesFragment : Fragment() {
    private var _binding: FragmentCuriosidadesBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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

        //variaveis de controle para o card que contém o texto HISTORIA
        val cardHistoria = binding.expandableTextHistory
        val botaoHistoria = binding.expandButtonHistory
        val textoHistoria = binding.hiddenTextHistoria

        //ação para mostrar o texto Hitória
        botaoHistoria.setOnClickListener {
            hideOrShowCard(
                textoHistoria,
                cardHistoria,
                botaoHistoria
            )
        }

        //variaveis de controle para o card que contém o texto NOME
        val cardNome = binding.expandableTextName
        val botaoNome = binding.expandButtonName
        val textoNome = binding.hiddenTextName

        //ação para mostrar o texto NOME
        botaoNome.setOnClickListener {
            hideOrShowCard(
                textoNome,
                cardNome,
                botaoNome
            )
        }

        //variaveis de controle para o card que contém o texto CLIMA
        val cardClima = binding.expandableTextClima
        val botaoClima = binding.expandButtonClima
        val textoClima = binding.hiddenTextClima

        //ação para mostrar o texto CLIMA
        botaoClima.setOnClickListener {
            hideOrShowCard(
                textoClima,
                cardClima,
                botaoClima
            )
        }

        //variaveis de controle para o card que contém o texto SOLO
        val cardSolo = binding.expandableTextSolo
        val botaoSolo = binding.expandButtonSolo
        val textoSolo = binding.hiddenTextSolo

        //ação para mostrar o texto SOLO
        botaoSolo.setOnClickListener {
            hideOrShowCard(
                textoSolo,
                cardSolo,
                botaoSolo
            )
        }

        //variaveis de controle para o card que contém o texto SATELITES
        val cardSatelites = binding.expandableTextSatelites
        val botaoSatelites = binding.expandButtonSatelites
        val textoSatelites = binding.hiddenTextSatelites

        //ação para mostrar o texto SATELITES
        botaoSatelites.setOnClickListener {
            hideOrShowCard(
                textoSatelites,
                cardSatelites,
                botaoSatelites
            )
        }

        //variaveis de controle para o card que contém o texto ATMOSFERA
        val cardAtmosfera = binding.expandableTextAtmosfera
        val botaoAtmosfera = binding.expandButtonAtmosfera
        val textoAtmosfera = binding.hiddenTextAtmosfera

        //ação para mostrar o texto ATMOSFERA
        botaoAtmosfera.setOnClickListener {
            hideOrShowCard(
                textoAtmosfera,
                cardAtmosfera,
                botaoAtmosfera
            )
        }

        //PARTE DE CURIOSIDADES

        //variaveis de controle para o card que contém o texto CYDONIA
        val cardCydonia = binding.expandableTextCydonia
        val botaoCydonia = binding.expandButtonCydonia
        val imgCydonia = binding.imgCydonia
        val textoCydonia = binding.hiddenTextCydonia

        //ação para mostrar o texto CYDONIA
        botaoCydonia.setOnClickListener {
            hideOrShowCard(
                textoCydonia,
                cardCydonia,
                botaoCydonia,
                listOf(imgCydonia)
            )
        }

        //variaveis de controle para o card que contém o texto HAPPY FACE
        val cardHappyFace = binding.expandableTextHappyFace
        val botaoHappyFace = binding.expandButtonHappyFace
        val imgHappyFace = binding.imgHappyFace
        val textoHappyFace = binding.hiddenTextHappyFace

        //ação para mostrar o texto HAPPY FACE
        botaoHappyFace.setOnClickListener {
            hideOrShowCard(
                textoHappyFace,
                cardHappyFace,
                botaoHappyFace,
                listOf(imgHappyFace)
            )
        }

        //variaveis de controle para o card que contém o texto BUTTERFLY
        val cardButterfly = binding.expandableTextButterfly
        val botaoButterfly = binding.expandButtonButterfly
        val imgButterfly = binding.imgButterfly
        val textoButterfly = binding.hiddenTextButterfly

        //ação para mostrar o texto BUTTERFLY
        botaoButterfly.setOnClickListener {
            hideOrShowCard(
                textoButterfly,
                cardButterfly,
                botaoButterfly,
                listOf(imgButterfly)
            )
        }

        //variaveis de controle para o card que contém o texto ANEL DE MARTE
        val cardAnelMarte = binding.expandableTextAnelMarte
        val botaoAnelMarte = binding.expandButtonAnelMarte
        val textoAnelMarte = binding.hiddenTextAnelMarte

        //ação para mostrar o texto ANEL DE MARTE
        botaoAnelMarte.setOnClickListener {
            hideOrShowCard(
                textoAnelMarte,
                cardAnelMarte,
                botaoAnelMarte
            )
        }

        //variaveis de controle para o card que contém o texto CURIOSIDADES GEOGRAFICAS
        val cardCuriosidadesGeograficas = binding.expandableTextCuriosidadesGeograficas
        val botaoCuriosidadesGeograficas = binding.expandButtonCuriosidadesGeograficas
        val textoCuriosidadesGeograficas = binding.hiddenTextCuriosidadesGeograficas

        //ação para mostrar o texto CURIOSIDADES GEOGRAFICAS
        botaoCuriosidadesGeograficas.setOnClickListener {
            hideOrShowCard(
                textoCuriosidadesGeograficas,
                cardCuriosidadesGeograficas,
                botaoCuriosidadesGeograficas
            )
        }
    }

    /**
     * PT-BR
     * Mostra os itens de um card com animação dropdown. O que decide se a lista vai ser mostrada
     * ou não é o judge, basicamente uma View, na qual será verificado o atributo visibility para
     * decidir se vai ou não mostrar na tela os elementos.
     *
     * EN-US
     * Show items from a card with a dropdown animation. The decision of showing or not is done
     * with the judge variable, basically a View, which we verify the visibility attribute to
     * decide if we're going the elements on the screen or not.
     *
     * @author Jomar Júnior / Angelica (overall logic) | (Lógica geral)
     *
     * @param judge - Decide se vai ou não mostrar na tela os elementos
     *                Decides if it's going to show or not on the screen its elements
     * @param card - Card que recebe a animação
     *               Card which receives the animation
     * @param button - Altera a posição da seta de acordo com o estado do elemento judge
     *                 Alters the position of the arrow accordingly to the judge state.
     * @param thingsToShowOrNot - Qualquer outra view dentro do card que precisa aparecer na tela
     *                            Any view which is inside the card and needs to appear on the
     *                            screen
     */
    private fun hideOrShowCard(judge: View, card: CardView, button: ImageView,
                               thingsToShowOrNot: List<View> = listOf()) {
        when(judge.visibility) {
            View.VISIBLE -> {
                judge.visibility = View.GONE
                for (i in thingsToShowOrNot) {
                    i.visibility = View.GONE
                }
                TransitionManager.beginDelayedTransition(card, AutoTransition())
                button.setImageResource(R.drawable.ic_arrow_down_white)
            }

            else -> {
                TransitionManager.beginDelayedTransition(card, AutoTransition())
                button.setImageResource(R.drawable.ic_arrow_up_white)
                judge.visibility = View.VISIBLE
                for (i in thingsToShowOrNot) {
                    i.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}