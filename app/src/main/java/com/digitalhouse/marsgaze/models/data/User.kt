package com.digitalhouse.marsgaze.models.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.digitalhouse.marsgaze.helper.MessageHash

/**
 * PT-BR
 * Usuário do nosso aplicativo
 *
 * Importante: Sempre que criar um novo usuário, seja para salvar ou fazer qualquer outra coisa,
 * favor utilizar o método hashAndSalt para criar um hash da senha.
 *
 * EN-US
 * User of our app
 *
 * Important: Whenever a new user is created, be it to save on the DB or anything else, mind to
 * use the method hashAndSalt to create a hash password.
 *
 * @author Jomar Júnior
 *
 * @property id Id do usuário / User id
 * @property name Nome do usuário / User name
 * @property email Email do usuário / User email
 * @property password Senha do usuário / User password
 */
@Entity(tableName = "user") class User(
    @PrimaryKey
    val email: String,
    val name: String,
    var password: String,
) : MessageHash(password) {
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

