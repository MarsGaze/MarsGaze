package com.digitalhouse.marsgaze.models.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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
 * @property createdOn Data de criação do usuário / User birthday
 */
@Entity(tableName = "user") class User(
    @PrimaryKey
    var email: String,
    var name: String,
    var password: String,
    @ColumnInfo(name = "created_on", defaultValue = "DATE(\"now\")")
    var createdOn: String = ""
)

