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

        //ação para mostrar o texto NOME
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

        //ação para mostrar o texto NOME
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

        //ação para mostrar o texto NOME
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

        //ação para mostrar o texto NOME
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
    }
}