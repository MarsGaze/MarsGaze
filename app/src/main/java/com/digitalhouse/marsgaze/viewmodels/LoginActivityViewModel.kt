package com.digitalhouse.marsgaze.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class LoginActivityViewModel : ViewModel() {
    /**
     * PT-BR
     * Verifica se é a primeira vez que está utilizando o app.
     *
     * EN-US
     * Verifies if it's the first time using the app.
     *
     * @param Context Contexto da aplicação
     *                App Context
     *
     * @return Boolean Verdadeiro se sim senão falso.
     *                 True if yes otherwise false
     */
    fun firstTimeUse(context: Context): Boolean {
        val path = context.filesDir.absolutePath.plus("was_ran")

        val filePath = File(path)

        val existsNgt = !filePath.exists()

        if (existsNgt) {
            viewModelScope.launch(Dispatchers.IO) {
                if (!filePath.createNewFile()) {
                    Log.e("START-UP", "Não foi possível criar o arquivo de inicialização")
                }
            }
        }

        return existsNgt
    }
}