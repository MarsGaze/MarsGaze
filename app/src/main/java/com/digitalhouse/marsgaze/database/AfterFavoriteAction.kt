package com.digitalhouse.marsgaze.database

import android.content.Context
import android.util.Log
import com.digitalhouse.marsgaze.database.dao.FavoriteDAO
import com.digitalhouse.marsgaze.models.data.FavoriteType
import java.io.File

/**
 * PT-BR
 * Implementação da ação após alguma mudança no banco de dados dos favoritos. Caso ele tenha sido
 * inserido, será verificado se o arquivo do favorito já existe senão ele será criado. Tal arquivo
 * é compartilhado com todos os usuários. Caso tenha que ser deletado o favorito, será verificado
 * se outros usuários estão utilizando, caso estejam o arquivo será mantido senão não.
 *
 * EN-US
 * Implementation of the behavior after a favorite change in the database. If a new favorite is
 * inserted, there shall be a verification to ensure that no file is alredy created otherwise
 * it will be created. This file is shared across all users. After a deletion of a favorite,
 * a verification of the favorite usage between the users will be done and when it's found any
 * user is using it, it will be kept otherwise deleted.
 *
 * @property context Contexto para pegar o caminho do aplicativo.
 *                Context to take the app path.
 *
 *
 * @property favoriteDAO Usado para verificar se algum usuário está utilizando o favorito
 *                    Used to verify if an user is using the favorite
 *
 */
class AfterFavoriteAction(
    private val context: Context,
    private val favoriteDAO: FavoriteDAO = MarsGazeDB.getDatabase(context).favoriteDAO()
    ) : FavoriteHelperAfter {
    companion object {
        const val FOLDER = "favorites"
        const val ROVER = "rover"
        const val HUBBLE = "hubble"
    }


    private fun getFavFolder(path: String): File {
        val mainFolder = context.filesDir.path

        val folder = File("$mainFolder/$FOLDER/$path")

        if(folder.exists() && folder.isFile) {
            Log.w(
                "FAV_ACT_FOLDER",
                "O arquivo deveria ser uma pasta e não um arquivo comum"
            )
        } else if (!folder.exists()) {
            folder.mkdirs()
        }

        return folder
    }


    /**
     * PT-BR
     * Pega o arquivo do favorito que contém os dados completos do tipo do favorito.
     *
     * EN-US
     * Picks a file of the favorite which contains the complete data of the favorite type.
     *
     * @param favType Tipo do favorito
     *                Favorite type
     *
     * @param fileName Id do favorito
     *                 Favorite id
     *
     * @return Arquivo do favorito
     *         Favorite file
     *
     */
    fun getFavFile(favType: FavoriteType, fileName: String): File {
        return when (favType) {
            FavoriteType.HUBBLE_IMAGE ->
                File("${getFavFolder(HUBBLE).path}/$fileName.json")
            FavoriteType.ROVERS_IMAGE ->
                File("${getFavFolder(ROVER).path}/$fileName.json")
        }
    }

    /**
     * @see FavoriteHelperAfter.afterDelete
     */
    override fun afterDelete(favType: FavoriteType, name: String): List<Boolean> {
        if (favoriteDAO.usersUsingImage(favType.ordinal, name).isEmpty()) {
            val file = getFavFile(favType, name)
            if (file.exists() && file.isFile) {
                return listOf(true, file.delete())
            }

            return listOf(true, false)
        }

        return listOf(false, false)
    }

    /**
     * @see FavoriteHelperAfter.afterInsert
     */
    override fun afterInsert(favType: FavoriteType, name: String, data: String): List<Boolean> {
        val file = getFavFile(favType, name)
        if (!file.exists()) {
            file.writeText(data)

            return listOf(true, true)
        }

        return listOf(false, false)
    }

    /**
     * @see FavoriteHelperAfter.afterUpdate
     */
    override fun afterUpdate(favType: FavoriteType, name: String): List<Boolean> {
        return listOf(false, false)
    }
}