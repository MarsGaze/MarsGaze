package com.digitalhouse.marsgaze.database.dao

import androidx.room.*
import com.digitalhouse.marsgaze.models.data.FavoriteTest

/**
 * PT-BR
 * Interface de acesso ao banco de dados para os favoritos. É provido somente a adição e remoção
 * deles.
 *
 * EN-US
 * Interface to access the database related to favorites. Here we only have the insert and delete
 * method.
 *
 * @author Jomar Júnior
 */
@Dao interface FavoriteDAO {
    /**
     * PT-BR
     * Insere um favorito atrelado a um usuário. Caso o item já esteja favoritado abortamos a ação.
     *
     * EN-US
     * Inserts a favorite bound to the user. If the item is favourited already we abort the action.
     *
     * @param favorite O favorito a ser adicionado
     *                 The favorite to be added
     *
     * @return O id do favorito
     *         The id of the favorite
     */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(favorite: FavoriteTest): Long

    /**
     * PT-BR
     * Deleta um favorito atrelado a um usuário. Não retorna erros caso o item a ser deletado não
     * exista
     *
     * EN-US
     * Deletes a favorite bound to an user. No error is returned in case of the item does not exist.
     *
     * @param favorite O favorito a ser deletado
     *                 The favorite to delete
     */
    @Delete
    fun delete(favorite: FavoriteTest)


    /**
     * PT-BR
     * Retorna todos os favoritos usando esse elemento
     *
     * EN-US
     * Returns all favorites using this element
     *
     * @param imageType O tipo do favorito
     *                  The favorite type
     *
     * @param imageId O id do favorito
     *                The favorite id
     *
     * @return lista de favoritos
     *         favorite list
     */
    @Query("SELECT * FROM favorite WHERE image_type = :imageType AND imageId = :imageId")
    fun usersUsingImage(imageType: Int, imageId: String): List<FavoriteTest>

    /**
     * PT-BR
     * Retorna se o usuário contém o favorito.
     *
     * EN-US
     * Returns if the user contains the favorite
     *
     * @param user usuario logado da sessão
     *             user in session
     *
     * @param imageType O tipo do favorito
     *                  The favorite type
     *
     * @param imageName O id do favorito
     *                The favorite id
     *
     * @return favorito se existe senão nulo
     *         favorite if it exists otherwise null
     */
    @Query("SELECT * FROM favorite WHERE user = :user AND image_type = :imageType AND " +
                 "imageId = :imageName")
    fun favoritedImage(
        user: String,
        imageType: Int,
        imageName: String
    ): FavoriteTest?

    /**
     * PT-BR
     * Retorna todos os favoritos do usuário
     *
     * EN-US
     * Returns all the favorites of the user.
     *
     * @param user usuario logado da sessão (email)
     *             user in session (email)
     *
     * @return Todos os favoritos do usuário
     *         All favorites of the user
     */
    @Query("SELECT * FROM favorite WHERE user = :user")
    fun getUserFavorites(
        user: String // Email do usuário
    ): List<FavoriteTest>
}