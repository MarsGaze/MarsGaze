package com.digitalhouse.marsgaze.models.hubble

import com.digitalhouse.marsgaze.models.data.Favorite
import com.digitalhouse.marsgaze.models.data.FavoriteType
import com.digitalhouse.marsgaze.models.data.User
import com.digitalhouse.marsgaze.models.favorite.ImageDetailAdapter
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class HubbleResponse(
    val collection: PhotoCollection = PhotoCollection(mutableListOf())
)

data class PhotoCollection(
    val items: MutableList<Item>
)

data class Item(
    val links: List<Link>,
    @SerializedName("href") val itemHref: String,
    val `data`: List<Data>
): Serializable, ImageDetailAdapter {
    override fun getTitle(): String = data[0].title

    override fun getImg(): String = links[0].linkHref

    override fun getDesc(): String = data[0].description

    override fun getId(): String = data[0].nasa_id

    override fun getType(): Int = FavoriteType.HUBBLE_IMAGE.ordinal

    override fun getExtraInfo(): String = data[0].date_created.substring(0, 10)

    override fun toFavorite(user: User): Favorite = Favorite(
        null,
        getType(),
        getId(),
        user.email,
        getImg()
    )
}

data class Link (
    val render: String,
    @SerializedName("href") val linkHref: String,           // IMPORTANT
    val rel: String
): Serializable

data class Data (
    val media_type: String,
    val center: String,
    val nasa_id: String,
    val description: String,    // IMPORTANT
    val keywords: ArrayList<String>,
    val date_created: String,
    val title: String           // IMPORTANT
): Serializable