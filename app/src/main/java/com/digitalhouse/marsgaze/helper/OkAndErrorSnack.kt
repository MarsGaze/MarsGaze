package com.digitalhouse.marsgaze.helper

import android.content.Context
import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar

/**
 * @see SnackCreator
 *
 * @property view A view que o snack bar vai aparecer
 *             The view which the snack bar will appear on.
 *
 * @property context Contexto do app
 *                App context
 */
class OkAndErrorSnack(
    private val view: View,
    private val context: Context
) : SnackCreator {
    /**
     * @see SnackCreator.showSnack
     */
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