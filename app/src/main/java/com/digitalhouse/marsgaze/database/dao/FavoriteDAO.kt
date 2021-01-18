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
}