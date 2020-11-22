package com.digitalhouse.marsgaze.ui

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.digitalhouse.marsgaze.R
import kotlinx.android.synthetic.main.activity_curiosidades.*

class CuriosidadesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_curiosidades)

        //botão de voltar
        iv_backArrowCuriosidades.setOnClickListener {
            finish()
        }

        //variaveis de controle para o card que contém o texto HISTORIA
        val cardHistoria = findViewById<CardView>(R.id.expandable_text_history)
        val botaoHistoria = findViewById<ImageView>(R.id.expand_button_history)
        val textoHistoria = findViewById<TextView>(R.id.hidden_text_historia)

        //ação para mostrar o texto Hitória
        botaoHistoria.setOnClickListener {
             when(textoHistoria.visibility) {
                 View.VISIBLE -> {
                     textoHistoria.visibility = View.GONE
                     TransitionManager.beginDelayedTransition(cardHistoria, AutoTransition())
                     botaoHistoria.setImageResource(R.drawable.ic_arrow_down_white)
                 }

                 else -> {
                     TransitionManager.beginDelayedTransition(cardHistoria, AutoTransition())
                     botaoHistoria.setImageResource(R.drawable.ic_arrow_up_white)
                     textoHistoria.visibility = View.VISIBLE
                 }
             }
        }

        //variaveis de controle para o card que contém o texto NOME
        val cardNome = findViewById<CardView>(R.id.expandable_text_name)
        val botaoNome = findViewById<ImageView>(R.id.expand_button_name)
        val textoNome = findViewById<TextView>(R.id.hidden_text_name)

        //ação para mostrar o texto NOME
        botaoNome.setOnClickListener {
            when(textoNome.visibility) {
                View.VISIBLE -> {
                    textoNome.visibility = View.GONE
                    TransitionManager.beginDelayedTransition(cardNome, AutoTransition())
                    expand_button_name.setImageResource(R.drawable.ic_arrow_down_white)
                }

                else -> {
                    TransitionManager.beginDelayedTransition(cardNome, AutoTransition())
                    expand_button_name.setImageResource(R.drawable.ic_arrow_up_white)
                    textoNome.visibility = View.VISIBLE
                }
            }
        }

        //variaveis de controle para o card que contém o texto CLIMA
        val cardClima = findViewById<CardView>(R.id.expandable_text_clima)
        val botaoClima = findViewById<ImageView>(R.id.expand_button_clima)
        val textoClima = findViewById<TextView>(R.id.hidden_text_clima)

        //ação para mostrar o texto CLIMA
        botaoClima.setOnClickListener {
            when(textoClima.visibility) {
                View.VISIBLE -> {
                    textoClima.visibility = View.GONE
                    TransitionManager.beginDelayedTransition(cardClima, AutoTransition())
                    botaoClima.setImageResource(R.drawable.ic_arrow_down_white)
                }

                else -> {
                    TransitionManager.beginDelayedTransition(cardClima, AutoTransition())
                    botaoClima.setImageResource(R.drawable.ic_arrow_up_white)
                    textoClima.visibility = View.VISIBLE
                }
            }
        }

        //variaveis de controle para o card que contém o texto SOLO
        val cardSolo = findViewById<CardView>(R.id.expandable_text_solo)
        val botaoSolo = findViewById<ImageView>(R.id.expand_button_solo)
        val textoSolo = findViewById<TextView>(R.id.hidden_text_solo)

        //ação para mostrar o texto SOLO
        botaoSolo.setOnClickListener {
            when(textoSolo.visibility) {
                View.VISIBLE -> {
                    textoSolo.visibility = View.GONE
                    TransitionManager.beginDelayedTransition(cardSolo, AutoTransition())
                    botaoSolo.setImageResource(R.drawable.ic_arrow_down_white)
                }

                else -> {
                    TransitionManager.beginDelayedTransition(cardSolo, AutoTransition())
                    botaoSolo.setImageResource(R.drawable.ic_arrow_up_white)
                    textoSolo.visibility = View.VISIBLE
                }
            }
        }

        //variaveis de controle para o card que contém o texto SATELITES
        val cardSatelites = findViewById<CardView>(R.id.expandable_text_satelites)
        val botaoSatelites = findViewById<ImageView>(R.id.expand_button_satelites)
        val textoSatelites = findViewById<TextView>(R.id.hidden_text_satelites)

        //ação para mostrar o texto SATELITES
        botaoSatelites.setOnClickListener {
            when(textoSatelites.visibility) {
                View.VISIBLE -> {
                    textoSatelites.visibility = View.GONE
                    TransitionManager.beginDelayedTransition(cardSatelites, AutoTransition())
                    botaoSatelites.setImageResource(R.drawable.ic_arrow_down_white)
                }

                else -> {
                    TransitionManager.beginDelayedTransition(cardSatelites, AutoTransition())
                    botaoSatelites.setImageResource(R.drawable.ic_arrow_up_white)
                    textoSatelites.visibility = View.VISIBLE
                }
            }
        }

        //variaveis de controle para o card que contém o texto ATMOSFERA
        val cardAtmosfera = findViewById<CardView>(R.id.expandable_text_atmosfera)
        val botaoAtmosfera = findViewById<ImageView>(R.id.expand_button_atmosfera)
        val textoAtmosfera = findViewById<TextView>(R.id.hidden_text_atmosfera)

        //ação para mostrar o texto ATMOSFERA
        botaoAtmosfera.setOnClickListener {
            when(textoAtmosfera.visibility) {
                View.VISIBLE -> {
                    textoAtmosfera.visibility = View.GONE
                    TransitionManager.beginDelayedTransition(cardAtmosfera, AutoTransition())
                    botaoAtmosfera.setImageResource(R.drawable.ic_arrow_down_white)
                }

                else -> {
                    TransitionManager.beginDelayedTransition(cardAtmosfera, AutoTransition())
                    botaoAtmosfera.setImageResource(R.drawable.ic_arrow_up_white)
                    textoAtmosfera.visibility = View.VISIBLE
                }
            }
        }

        //PARTE DE CURIOSIDADES

        //variaveis de controle para o card que contém o texto CYDONIA
        val cardCydonia = findViewById<CardView>(R.id.expandable_text_cydonia)
        val botaoCydonia = findViewById<ImageView>(R.id.expand_button_cydonia)
        val imgCydonia = findViewById<ImageView>(R.id.img_cydonia)
        val textoCydonia = findViewById<TextView>(R.id.hidden_text_cydonia)

        //ação para mostrar o texto CYDONIA
        botaoCydonia.setOnClickListener {
            when(textoCydonia.visibility) {
                View.VISIBLE -> {
                    textoCydonia.visibility = View.GONE
                    imgCydonia.visibility = View.GONE
                    TransitionManager.beginDelayedTransition(cardCydonia, AutoTransition())
                    botaoCydonia.setImageResource(R.drawable.ic_arrow_down_white)
                }

                else -> {
                    TransitionManager.beginDelayedTransition(cardCydonia, AutoTransition())
                    botaoCydonia.setImageResource(R.drawable.ic_arrow_up_white)
                    textoCydonia.visibility = View.VISIBLE
                    imgCydonia.visibility = View.VISIBLE
                }
            }
        }

        //variaveis de controle para o card que contém o texto HAPPY FACE
        val cardHappyFace = findViewById<CardView>(R.id.expandable_text_happyFace)
        val botaoHappyFace = findViewById<ImageView>(R.id.expand_button_happyFace)
        val imgHappyFace = findViewById<ImageView>(R.id.img_happyFace)
        val textoHappyFace = findViewById<TextView>(R.id.hidden_text_happyFace)

        //ação para mostrar o texto HAPPY FACE
        botaoHappyFace.setOnClickListener {
            when(textoHappyFace.visibility) {
                View.VISIBLE -> {
                    textoHappyFace.visibility = View.GONE
                    imgHappyFace.visibility = View.GONE
                    TransitionManager.beginDelayedTransition(cardHappyFace, AutoTransition())
                    botaoHappyFace.setImageResource(R.drawable.ic_arrow_down_white)
                }

                else -> {
                    TransitionManager.beginDelayedTransition(cardHappyFace, AutoTransition())
                    botaoHappyFace.setImageResource(R.drawable.ic_arrow_up_white)
                    textoHappyFace.visibility = View.VISIBLE
                    imgHappyFace.visibility = View.VISIBLE
                }
            }
        }

        //variaveis de controle para o card que contém o texto BUTTERFLY
        val cardButterfly = findViewById<CardView>(R.id.expandable_text_butterfly)
        val botaoButterfly = findViewById<ImageView>(R.id.expand_button_butterfly)
        val imgButterfly = findViewById<ImageView>(R.id.img_butterfly)
        val textoButterfly = findViewById<TextView>(R.id.hidden_text_butterfly)

        //ação para mostrar o texto BUTTERFLY
        botaoButterfly.setOnClickListener {
            when(textoButterfly.visibility) {
                View.VISIBLE -> {
                    textoButterfly.visibility = View.GONE
                    imgButterfly.visibility = View.GONE
                    TransitionManager.beginDelayedTransition(cardButterfly, AutoTransition())
                    botaoButterfly.setImageResource(R.drawable.ic_arrow_down_white)
                }

                else -> {
                    TransitionManager.beginDelayedTransition(cardButterfly, AutoTransition())
                    botaoButterfly.setImageResource(R.drawable.ic_arrow_up_white)
                    textoButterfly.visibility = View.VISIBLE
                    imgButterfly.visibility = View.VISIBLE
                }
            }
        }

        //variaveis de controle para o card que contém o texto ANEL DE MARTE
        val cardAnelMarte = findViewById<CardView>(R.id.expandable_text_anelMarte)
        val botaoAnelMarte = findViewById<ImageView>(R.id.expand_button_anelMarte)
        val textoAnelMarte = findViewById<TextView>(R.id.hidden_text_anelMarte)

        //ação para mostrar o texto ANEL DE MARTE
        botaoAnelMarte.setOnClickListener {
            when(textoAnelMarte.visibility) {
                View.VISIBLE -> {
                    textoAnelMarte.visibility = View.GONE
                    imgButterfly.visibility = View.GONE
                    TransitionManager.beginDelayedTransition(cardAnelMarte, AutoTransition())
                    botaoAnelMarte.setImageResource(R.drawable.ic_arrow_down_white)
                }

                else -> {
                    TransitionManager.beginDelayedTransition(cardAnelMarte, AutoTransition())
                    botaoAnelMarte.setImageResource(R.drawable.ic_arrow_up_white)
                    textoAnelMarte.visibility = View.VISIBLE
                    imgButterfly.visibility = View.VISIBLE
                }
            }
        }

        //variaveis de controle para o card que contém o texto CURIOSIDADES GEOGRAFICAS
        val cardCuriosidadesGeograficas = findViewById<CardView>(R.id.expandable_text_curiosidadesGeograficas)
        val botaoCuriosidadesGeograficas = findViewById<ImageView>(R.id.expand_button_curiosidadesGeograficas)
        val textoCuriosidadesGeograficas = findViewById<TextView>(R.id.hidden_text_curiosidadesGeograficas)

        //ação para mostrar o texto CURIOSIDADES GEOGRAFICAS
        botaoCuriosidadesGeograficas.setOnClickListener {
            when(textoCuriosidadesGeograficas.visibility) {
                View.VISIBLE -> {
                    textoCuriosidadesGeograficas.visibility = View.GONE
                    TransitionManager.beginDelayedTransition(cardCuriosidadesGeograficas, AutoTransition())
                    botaoCuriosidadesGeograficas.setImageResource(R.drawable.ic_arrow_down_white)
                }

                else -> {
                    TransitionManager.beginDelayedTransition(cardCuriosidadesGeograficas, AutoTransition())
                    botaoCuriosidadesGeograficas.setImageResource(R.drawable.ic_arrow_up_white)
                    textoCuriosidadesGeograficas.visibility = View.VISIBLE
                }
            }
        }
    }
}