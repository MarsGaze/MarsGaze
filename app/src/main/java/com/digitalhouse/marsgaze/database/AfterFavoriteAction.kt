package com.digitalhouse.marsgaze.database

import android.content.Context
import android.util.Log
import com.digitalhouse.marsgaze.database.dao.FavoriteDAO
import com.digitalhouse.marsgaze.models.data.FavoriteType
import com.digitalhouse.marsgaze.models.favorite.ImageDetailAdapter
import com.google.gson.JsonObject
import java.io.File

class AfterFavoriteAction(
    val context: Context,
    val favoriteDAO: FavoriteDAO = MarsGazeDB.getDatabase(context).favoriteDAO()
    ) : FavoriteHelperAfter {
    companion object {
        val FOLDER = "favorites"
        val ROVER = "rover"
        val HUBBLE = "hubble"
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

    fun getFavFile(favType: FavoriteType, fileName: String): File {
        return when (favType) {
            FavoriteType.HUBBLE_IMAGE ->
                File("${getFavFolder(HUBBLE).path}/$fileName.json")
            FavoriteType.ROVERS_IMAGE ->
                File("${getFavFolder(ROVER).path}/$fileName.json")
        }
    }

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

    override fun afterInsert(favType: FavoriteType, name: String, data: String): List<Boolean> {
        val file = getFavFile(favType, name)
        if (!file.exists()) {
            file.writeText(data)

            return listOf(true, true)
        }

        return listOf(false, false)
    }

    override fun afterUpdate(favType: FavoriteType, name: String): List<Boolean> {
        return listOf(false, false)
    }
}