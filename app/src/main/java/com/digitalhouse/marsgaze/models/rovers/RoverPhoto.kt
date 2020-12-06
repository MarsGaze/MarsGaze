package com.digitalhouse.marsgaze.models.rovers

import com.google.gson.annotations.SerializedName

/**
 * Data class representing a photo single from Mars Rover Photos.
 * Not all of the fields returned from the API are represented here.
 *
 */
data class RoverPhoto(
    val sol: Int,
    val camera: RoverCamera,
    @SerializedName("img_src") val imageUrl: String,
    @SerializedName("earth_date") val earthDate: String,
    val rover: Rover
)