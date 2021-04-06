package com.digitalhouse.marsgaze.models.favorite

import com.digitalhouse.marsgaze.models.data.Favorite
import com.digitalhouse.marsgaze.models.data.User

interface FavoriteDetailAdapter : ImageDetailAdapter {
    fun isFavorited(): Boolean

    fun parentAdapter(): ImageDetailAdapter

    companion object {
        fun imageDetailAdapterToThis(adapter: ImageDetailAdapter, value: Boolean) :
                FavoriteDetailAdapter {
            return object : FavoriteDetailAdapter {
                override fun parentAdapter(): ImageDetailAdapter {
                    return adapter
                }

                override fun isFavorited(): Boolean {
                    return value
                }

                override fun getTitle(): String {
                    return adapter.getTitle()
                }

                override fun getImg(): String {
                    return adapter.getImg()
                }

                override fun getDesc(): String {
                    return adapter.getDesc()
                }

                override fun getId(): String {
                    return adapter.getId()
                }

                override fun getExtraInfo(): String? {
                    return adapter.getExtraInfo()
                }

                override fun getType(): Int {
                    return adapter.getType()
                }

                override fun toFavorite(user: User): Favorite {
                    return adapter.toFavorite(user)
                }
            }
        }
    }
}