package com.digitalhouse.marsgaze.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalhouse.marsgaze.models.InsightInfo
import com.digitalhouse.marsgaze.models.Pressure
import com.digitalhouse.marsgaze.models.Temperature
import com.digitalhouse.marsgaze.services.InsightService
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

class InsightViewModel(private val repository: InsightService) : ViewModel() {
    val insightResponse = MutableLiveData<ArrayList<InsightInfo>>()

    fun getInsightInfo() {
        viewModelScope.launch {
            val response = repository.getInsightResponse()
            Log.i("reponse", response.toString())
            insightResponse.value = parseJson(response)
        }
    }

    private fun parseJson(jsonElement: JsonObject): ArrayList<InsightInfo> {
        val infoList = ArrayList<InsightInfo>()
        val solKeys = jsonElement.get("sol_keys").asJsonArray
        Log.i("sol_keys", solKeys.toString())

        // Gets each Sol object and puts into a map
        for (i in 0 until solKeys.size()) {
            Log.i("sol", solKeys[i].asString)
            val solObject = jsonElement.get(solKeys[i].asString).asJsonObject
            Log.i("solObject", solObject.toString())
            val info = InsightInfo(PRE = Pressure(), AT = Temperature())

            // Getting and setting Earth date and season values
            info.firstUTC = solObject.asJsonObject.get("First_UTC").asString.substring(0, 10)
            info.lastUTC = solObject.asJsonObject.get("Last_UTC").asString.substring(0, 10)
            info.season = solObject.asJsonObject.get("Season").asString

            info.sol = solKeys[i].asString

            /**
             * Getting and setting atmospheric temperature (AT) values
             * TODO: use solObject.has()
             */
            if (
                try {
                    !solObject.getAsJsonObject("AT").isJsonNull
                } catch (ex: Exception) {
                    false
                }
            ) {
                info.AT.apply {
                    val at = solObject.get("AT").asJsonObject
                    av = at.get("av").asString.split(".")[0]
                    mn = at.get("mn").asString.split(".")[0]
                    mx = at.get("mx").asString.split(".")[0]
                    ct = at.get("ct").asString
                }
            }

            // Getting and setting pressure (PRE) values
            if (
                try {
                    !solObject.getAsJsonObject("PRE").isJsonNull
                } catch (ex: Exception) {
                    false
                }
            ) {
                info.PRE.apply {
                    val at = solObject.get("PRE").asJsonObject
                    av = at.get("av").asString.split(".")[0]
                    mn = at.get("mn").asString.split(".")[0]
                    mx = at.get("mx").asString.split(".")[0]
                    ct = at.get("ct").asString
                }
            }

            // Populating our map
            infoList.add(info)
        }

        Log.i("map", infoList.toString())
        return infoList
    }
}
