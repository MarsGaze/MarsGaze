package com.digitalhouse.marsgaze.controllers.service

import android.util.Log
import com.digitalhouse.marsgaze.models.insight.InsightInfo
import com.digitalhouse.marsgaze.services.InsightService
import com.google.gson.JsonObject
import retrofit2.Response

class InsightController private constructor(private val insightService: InsightService) :
        ServiceController<InsightType>() {

    init {
        this.calls[InsightType.INSIGHT] = ::getInsightStub
    }

    suspend fun getInsight(useCache: Boolean = false): Response<ArrayList<InsightInfo>>? {
        if (useCache && cache[InsightType.INSIGHT] != null) {
            @Suppress("UNCHECKED_CAST")
            return cache[InsightType.INSIGHT] as Response<ArrayList<InsightInfo>>
        }

        return call(InsightType.INSIGHT)
    }

    suspend fun cacheInsight() {
        cacheCall<ArrayList<InsightInfo>>(InsightType.INSIGHT)
    }

    companion object {
        var obj: InsightController? = null

        fun getController(service: InsightService = InsightService.create()): InsightController {
            if (obj != null) {
                return obj!!
            }

            obj = InsightController(service)
            return obj!!
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

            val info = InsightInfo.fromJson(solObject)
            info.sol = solKeys[i].asString
            // Populating our map
            infoList.add(info)
        }

        Log.i("map", infoList.toString())
        return infoList
    }

    private suspend fun getInsightStub(): Response<ArrayList<InsightInfo>> {
        val response = insightService.getInsightResponse()
        val body = response.body()

        if (body != null && response.isSuccessful) {
            val insightAnswer = parseJson(body)
            return Response.success(insightAnswer, response.raw())
        }

        return Response.error(response.errorBody()!!, response.raw())
    }
}

enum class InsightType{
    INSIGHT
}