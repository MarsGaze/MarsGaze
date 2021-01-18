package com.digitalhouse.marsgaze.models.rovers

import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 * Data class representing a response from Mars Rovers Photos API.
 * "latest_photos" endpoint response comes inside a "latest_photos" JSON object instead of "photos".
 *
 */
data class RoverResponse(
    @SerializedName("photos", alternate = ["latest_photos"]) val photos: List<RoverPhoto> = listOf()
)