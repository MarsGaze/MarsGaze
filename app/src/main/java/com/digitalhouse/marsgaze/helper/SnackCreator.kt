package com.digitalhouse.marsgaze.helper

import android.content.Context
import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar

/**
 * PT-BR
 * Cria snacks com cores e mensagens diferentes com o paramêtro passado.
 *
 * EN-US
 * Create snacks with colors and messages differents from the parameter given
 *
 * @author Jomar Júnior
 */
interface SnackCreator {
    fun showSnack(value: Pair<Boolean, Int>): Snackbar
}

// TODO Documentação e provavelmente um meio mais bonito de realizar essa tarefa aqui né
class OkAndErrorSnack(
    private val view: View,
    private val context: Context
) : SnackCreator {
    override fun showSnack(value: Pair<Boolean, Int>): Snackbar {
        val snack = Snackbar.make(
            context,
            view,
            context.getString(value.second),
            Snackbar.LENGTH_SHORT
        )

        snack.show()

        if (value.first) {
            snack.setBackgroundTint(Color.GREEN)
        } else {
            snack.setBackgroundTint(Color.RED)
        }

        return snack
    }
}