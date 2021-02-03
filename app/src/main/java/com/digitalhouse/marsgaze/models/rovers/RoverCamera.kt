package com.digitalhouse.marsgaze.models.rovers

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Data class that represents the camera associated with a photo from Mars Rover Photos.
 *
 */
data class RoverCamera(
	@SerializedName("name") val abbrName: String,
	@SerializedName("full_name") val fullName: String
) : Serializable