package com.digitalhouse.marsgaze.helper


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
    /**
     * PT-BR
     * Mostra a snack bar de acordo com os valores passados.
     *
     * EN-US
     * Shows a snack bar accordingly to the given values
     *
     * @param value Par de valores onde o primeiro é se foi ok e o segundo o id do recurso de string
     *              pair where the first value is to check if its ok and the second is the id of the
     *              string resource
     *
     * @return A snack criada
     *         The created snack
     */
    fun showSnack(value: Pair<Boolean, Int>): Snackbar
}