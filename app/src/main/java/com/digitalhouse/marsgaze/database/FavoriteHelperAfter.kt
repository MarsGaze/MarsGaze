package com.digitalhouse.marsgaze.database

import com.digitalhouse.marsgaze.models.data.FavoriteType

/**
 * PT-BR
 * Realiza ações especificas voltadas para as ações de favoritos. Usado para salvar arquivos após
 * uma ação no banco.
 *
 * EN-US
 * Do a few specifics actions related to the favorite model. It is normally used to save files
 * after a database transcation.
 *
 * @author Jomar Júnior
 */
interface FavoriteHelperAfter {
    /**
     * PT-BR
     * Deleta o arquivo do favorito caso nenhum usuário esteja utilizando ele.
     *
     * EN-US
     * Deletes the file if no user is using it.
     *
     * @param favType   O tipo do favorito
     *                  The type of the Favorite model
     * @param name      O id do favorito
     *                  The id of the favorite
     * @return Uma lista com dois elementos boleanos. O primeiro se trata se o arquivo existe e o
     *         segundo se foi deletado com sucesso o arquivo
     *         A list with two booleans elements. The first verifies if the exists and the second
     *         is the status of the file deletion.
     */
    fun afterDelete(favType: FavoriteType, name: String): List<Boolean>

    /**
     * PT-BR
     * Cria um arquivo do favorito
     *
     * EN-US
     * Creates a file of the favorite model.
     *
     * EN-US
     *
     * @param favType   O tipo do favorito
     *                  The type of the Favorite model
     * @param name      O id do favorito
     *                  The id of the favorite
     * @param data      Data para ser inserida no arquivo
     *                  Data to be inserted on the file
     *
     * @return Uma lista com dois elementos boleanos. O primeiro se trata se o arquivo existe e o
     *         segundo se foi criado com sucesso o arquivo
     *         A list with two booleans elements. The first verifies if the exists and the second
     *         is the status of the file creation.
     */
    fun afterInsert(favType: FavoriteType, name: String, data: String): List<Boolean>

    /**
     * PT-BR
     * Atualiza o arquivo do favorito
     *
     * EN-US
     * Updates the favorite file
     *
     * @param favType   O tipo do favorito
     *                  The type of the Favorite model
     * @param name      O id do favorito
     *                  The id of the favorite
     * @return Uma lista com dois elementos boleanos. O primeiro se trata se o arquivo existe e o
     *         segundo se foi atualizado com sucesso o arquivo
     *         A list with two booleans elements. The first verifies if the exists and the second
     *         is the status of the file update.
     */
    fun afterUpdate(favType: FavoriteType, name: String): List<Boolean>
}