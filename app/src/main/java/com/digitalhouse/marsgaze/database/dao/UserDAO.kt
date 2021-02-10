package com.digitalhouse.marsgaze.database.dao

import androidx.room.*
import com.digitalhouse.marsgaze.models.data.Favorite
import com.digitalhouse.marsgaze.models.data.User

/**
 * PT-BR
 * Interface de acesso ao banco de dados para os favoritos. É provido somente a adição e remoção
 * e outros métodos para pegar os favoritos do usuário e a verificação de login.
 *
 * EN-US
 * Interface to access the database related to favorites. Here we have the insert and delete and
 * a few other methods to get all the favorites of the user and the login verification.
 *
 * @author Jomar Júnior
 */
@Dao interface UserDAO {
    /**
     * PT-BR
     * Insere um usuário a tabela. Caso já tenha um usuário com o mesmo email abortamos a operação.
     *
     * EN-US
     * Inserts a user in the table. If there's already a user with the given email we abort the
     * operation.
     */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(user: User)

    /**
     * PT-BR
     * Deleta um usuário da tabela. Caso o usuário não exista não ocorrerá nenhum erro.
     *
     * EN-US
     * Deletes a user from the table. If the user doesn't exist no error will be given.
     */
    @Delete
    fun delete(user: User)

    /**
     * PT-BR
     * Atualiza o usuário da tabela. Caso o haja algum conflito de campos únicos retornara uma
     * exceção
     *
     * EN-US
     * Updates the user of the table. If any unique column conflicts an exception will be given.
     *
     * @param user Usuário a ser atualizado
     *             User to be updated
     */
    @Update
    fun update(user: User)

    /**
     * PT-BR
     * Pega todos os favoritos de um usuário.
     *
     * EN-US
     * Get all the favorites of the user.
     *
     * @param userEmail Email do usuário
     *                  User email
     *
     * @return Uma lista de favoritos do usuário
     *         A list of favorites from the user
     */
    @Query("SELECT * FROM favorite WHERE favorite.user = :userEmail")
    fun getFavorites(userEmail: String): List<Favorite>

    /**
     * PT-BR
     * Retorna um usuário caso a senha e o email batam com banco.
     *
     * EN-US
     * Returns an user if the password and email matches on the database.
     *
     * @param email Email do usuário a logar
     *              User email to login
     *
     * @param password Senha do usuário, em hash, a logar
     *                 User password, in hash, to login.
     *
     * @return Um usuário senão nulo
     *         An user otherwise null
     */
    @Query("SELECT * FROM user WHERE user.email = :email AND (user.password = :password or :password is NULL)")
    fun loginMatch(email: String, password: String?) : User?

    /**
     * PT-BR
     * Retorna o usuário que está na sessão mesmo que ela já tenha expirado
     *
     * EN-US
     * Returns the user in session even if its expired already.
     *
     * @return Usuário da sessão
     *         User in session
     */
    @Query("SELECT * FROM user WHERE user.in_session_until != 0")
    fun userInSession(): User?
}