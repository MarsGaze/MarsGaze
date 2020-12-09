package com.digitalhouse.marsgaze.models


data class InsightInfo(
    var sol: String = "NO_DATA",
    var firstUTC: String = "NO_DATA",
    var lastUTC: String = "NO_DATA",
    var PRE: Pressure,
    var AT: Temperature,
    var season: String = "NO_DATA"
)

data class Pressure(
    var av: String = "NO_DATA",
    var mn: String = "NO_DATA",
    var mx: String = "NO_DATA",
    var ct: String = "NO_DATA"
)

data class Temperature(
    var av: String = "NO_DATA",
    var mn: String = "NO_DATA",
    var mx: String = "NO_DATA",
    var ct: String = "NO_DATA"
)
