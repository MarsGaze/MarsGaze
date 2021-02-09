package com.digitalhouse.marsgaze.models.data

import androidx.room.*

/**
 * PT-BR
 * O Favorito, assim por se dizer, é o conteudo provido de um rover na qual
 * o usuário gostou.
 *
 * EN-US
 * Favorite, as per say, is the content of an image from a rover which the
 * the user gave liked.
 *
 * @property id Não usado mas necessario para conter favoritos únicos
 *           Not used but necessary for unique favorites
 *
 * @property imageType Tipo do favorito @see FavoriteType
 *                  Favorite type @see FavoriteType
 *
 * @property imageId Id vindo do favorito
 *                Id from the favorite
 *
 * @property user O email do usuário
 *             The user email
 *
 * @property imgPath Caminho da imagem (URI)
 *                Image path (URI)
 */
@Entity(
    foreignKeys = [
        ForeignKey(entity = User::class, onDelete = ForeignKey.CASCADE, parentColumns = ["email"],
                   childColumns = ["user"])
    ],
    tableName = "Favorite",
    indices = [Index("user")]
)
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    @ColumnInfo(name = "image_type")
    val imageType: Int,
    val imageId: String,
    val user: String,
    val imgPath: String
)

enum class FavoriteType {
    ROVERS_IMAGE,
    HUBBLE_IMAGE
}