package com.digitalhouse.marsgaze.objects

import com.google.gson.annotations.SerializedName

/**
 * Data class that represents a photo from Mars Rover Photos.
 *
 * Not all of the fields returned from the API are represented here; only the ones used in this
 * project are listed below.
 *
 */
data class RoverPhoto(
    @SerializedName("sol") val sol: Int,
    val camera: RoverCamera,
    @SerializedName("img_src") val imageUrl: String,
    @SerializedName("earth_date") val earthDate: String,
    val rover: Rover
)