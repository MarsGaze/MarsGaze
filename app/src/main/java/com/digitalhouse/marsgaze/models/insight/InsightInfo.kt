package com.digitalhouse.marsgaze.models.insight

import com.google.gson.JsonObject
import java.io.Serializable


data class InsightInfo (
    var sol: String = "NO_DATA",
    var firstUTC: String = "NO_DATA",
    var lastUTC: String = "NO_DATA",
    var PRE: Pressure,
    var AT: Temperature,
    var season: String = "NO_DATA"
) : Serializable {
    companion object {
        fun fromJson(json: JsonObject): InsightInfo {
            val info = InsightInfo(PRE = Pressure(), AT = Temperature())

            // Getting and setting Earth date and season values
            info.firstUTC = json.asJsonObject.get("First_UTC").asString.substring(0, 10)
            info.lastUTC = json.asJsonObject.get("Last_UTC").asString.substring(0, 10)
            info.season = json.asJsonObject.get("Season").asString

            // Getting and setting atmospheric temperature (AT) values
            if (json.has("AT")) {
                info.AT.apply {
                    val at = json.get("AT").asJsonObject
                    av = at.get("av").asString.split(".")[0]
                    mn = at.get("mn").asString.split(".")[0]
                    mx = at.get("mx").asString.split(".")[0]
                    ct = at.get("ct").asString
                }
            }

            // Getting and setting pressure (PRE) values
            if (json.has("PRE")) {
                info.PRE.apply {
                    val at = json.get("PRE").asJsonObject
                    av = at.get("av").asString.split(".")[0]
                    mn = at.get("mn").asString.split(".")[0]
                    mx = at.get("mx").asString.split(".")[0]
                    ct = at.get("ct").asString
                }
            }
            
            return info
        }
    }
}

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
