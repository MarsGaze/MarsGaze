package com.digitalhouse.marsgaze.models.rovers
import com.digitalhouse.marsgaze.models.data.FavoriteTest
import com.digitalhouse.marsgaze.models.data.FavoriteType
import com.digitalhouse.marsgaze.models.data.User
import com.digitalhouse.marsgaze.models.favorite.ImageDetailAdapter
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Data class representing a photo single from Mars Rover Photos.
 * Not all of the fields returned from the API are represented here.
 *
 */
data class RoverPhoto(
    val id: Int,
    val sol: String,
    val camera: RoverCamera,
    @SerializedName("img_src")
    val imageUrl: String,
    @SerializedName("earth_date")
    val earthDate: String,
    val rover: Rover
) : Serializable, ImageDetailAdapter {
    override fun getTitle(): String = "Sol $sol"

    override fun getImg(): String = imageUrl

    override fun getDesc(): String = "${camera.abbrName} - ${camera.fullName}"

    override fun getExtraInfo(): String? = earthDate

    override fun getId(): String = id.toString()

    override fun getType(): Int = FavoriteType.ROVERS_IMAGE.ordinal

    override fun toFavorite(user: User): FavoriteTest = FavoriteTest(
        null,
        getType(),
        getId(),
        user.email,
        getImg()
    )
}