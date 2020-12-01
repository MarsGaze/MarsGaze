package com.digitalhouse.marsgaze.objects

import com.google.gson.annotations.SerializedName

data class RoverResponse(
    @SerializedName("photos", alternate = ["latest_photos"]) val photos: List<RoverPhoto>
)