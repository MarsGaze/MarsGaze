package com.digitalhouse.marsgaze.objects

import com.google.gson.annotations.SerializedName

/**
 * Data class that represents the camera associated with a particular
 * photo from Mars Rover Photos.
 *
 */
data class RoverCamera(
	@SerializedName("name") val abbrName: String,
	@SerializedName("full_name") val fullName: String
)