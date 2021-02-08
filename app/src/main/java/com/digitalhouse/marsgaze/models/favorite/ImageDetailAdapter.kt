package com.digitalhouse.marsgaze.models.favorite

import com.digitalhouse.marsgaze.models.data.Favorite
import com.digitalhouse.marsgaze.models.data.User
import java.io.Serializable

/**
 * PT-BR
 * Encarrega as classes de conseguir prover os seguintes dados para a utilização no detalhamento
 * das imagens
 *
 * EN-US
 * Ensure classes to provide the necessary data to use in the image details.
 *
 */
interface ImageDetailAdapter : Serializable {
    fun getTitle(): String

    fun getImg(): String

    fun getDesc(): String

    fun getId(): String

    fun getExtraInfo(): String?

    fun getType(): Int

    fun toFavorite(user: User): Favorite
}