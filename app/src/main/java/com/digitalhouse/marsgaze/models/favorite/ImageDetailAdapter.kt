package com.digitalhouse.marsgaze.models.favorite

import com.digitalhouse.marsgaze.models.data.FavoriteTest
import com.digitalhouse.marsgaze.models.data.User
import java.io.Serializable

interface ImageDetailAdapter : Serializable {
    fun getTitle(): String

    fun getImg(): String

    fun getDesc(): String

    fun getId(): String

    fun getExtraInfo(): String?

    fun getType(): Int

    fun toFavorite(user: User): FavoriteTest
}