package com.digitalhouse.marsgaze.models

import androidx.room.Entity
import com.digitalhouse.marsgaze.helper.MessageHash

@Entity
/**
 * PT-BR
 * Usuário do nosso aplicativo
 *
 * EN-US
 * User of our app
 *
 * @author Jomar Júnior
 *
 * @property id Id do usuário / User id
 * @property name Nome do usuário / User name
 * @property email Email do usuário / User email
 * @property password Senha do usuário / User password
 */
class User(
    val id: Int? = null,
    val name: String,
    val email: String,
    private val _password: String,
) : MessageHash(_password) {
    /**
     * PT-BR
     * Proxy de acesso ao _password. Não necessitamos recolocar o valor de password.
     *
     * EN-US
     * Acess proxy of _password. There's no need to reassign the password value
     */
    var password = _password
    private set

    /**
     * PT-BR
     * Realiza a operação de hashAndSalt de MessageHash e depois atribui o valor retornado para
     * password.
     *
     * EN-US
     * Calls the hashAndSalt from MessageHash and then assigns the returned value to password.
     *
     * @return valor de hashAndSalt | hashAndSalt Value
     */
    override fun hashAndSalt(): String {
        password = super.hashAndSalt()
        return password
    }
}

