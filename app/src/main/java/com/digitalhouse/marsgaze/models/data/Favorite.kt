package com.digitalhouse.marsgaze.models.data

import androidx.room.*
import com.digitalhouse.marsgaze.R

/**
 * Classe temporária para o elemneto Favorito
 *
 * O Favorito, assim por se dizer, é o conteudo provido de um rover na qual
 * o usuário gostou. Ela é temporária já que vai ser usado somente para os
 * visuais.
 *
 * Dummy class for a Favorite element.
 *
 * Favorite, as per say, is the content of an image from a rover which the
 * the user gave liked. This is a dummy ony as it will be used only for
 * visuals.
 */
class Favorite(val image: Int, val sol: Int, val rover: String) {
    companion object {
        fun generator(): ArrayList<Favorite> {
            val favorites = arrayListOf<Favorite>()
            for (i in 690..712) {
                var image = R.drawable.favorite_1
                if (i % 2 == 0) {
                   image = R.drawable.favorite_2
                }

                favorites.add(
                    Favorite(
                    image,
                    i,
                    "Curiosity"
                )
                )
            }

            return favorites
        }
    }
}

@Entity(
    foreignKeys = [
        ForeignKey(entity = User::class, onDelete = ForeignKey.CASCADE, parentColumns = ["email"],
                   childColumns = ["user"])
    ],
    tableName = "Favorite",
    indices = [Index("user")]
)
data class FavoriteTest(
    // Usamos identificador único a parte do id da imagem já que elas podem conflitar
    // em relação ao seu tipo
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "image_type")
    val imageType: Int,
    val imageId: Int,
    val user: String
)


data class RoverImage(
    val a: String
)

data class HubbleImage(
    val a: String
)