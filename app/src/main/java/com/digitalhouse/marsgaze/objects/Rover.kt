package com.digitalhouse.marsgaze.objects

import com.google.gson.annotations.SerializedName

/**
 * Data class that represents the Rover associated with a particular
 * photo from Mars Rover Photos.
 *
 */
data class  Rover(
    @SerializedName("name") val roverName: String,
    @SerializedName("landing_date") val landingDate: String,
    @SerializedName("launch_date") val launchDate: String,
    val status: String
)