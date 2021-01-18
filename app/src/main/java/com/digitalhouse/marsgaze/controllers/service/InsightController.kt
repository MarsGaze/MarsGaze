package com.digitalhouse.marsgaze.controllers.service

import android.util.Log
import com.digitalhouse.marsgaze.models.insight.InsightInfo
import com.digitalhouse.marsgaze.services.InsightService
import com.google.gson.JsonObject
import kotlinx.coroutines.Job
import retrofit2.Response

/**
 * PT-BR
 * Controlador de chamadas para a API de Insight
 *
 * EN-US
 * Controller for Insight API calls.
 *
 * @author Jomar Júnior
 *
 * @param insightService Interface com as chamadas para a API do Insight
 *                       Interface with the API calls for Insight
 *
 * TODO: No momento não verificamos chamadas que já estão ocorrendo ou possibilitamos a espera delas
 *  junto com o recebimento do valor de tal
 */
class InsightController private constructor(private val insightService: InsightService) :
        ServiceController<InsightType>() {
    init {
        // Define as chamadas disponíveis para a realização do controle
        this.calls[InsightType.INSIGHT] = ::getInsightStub
    }

    /**
     * PT-BR
     * Pega o valores do endpoint do insight.
     *
     * EN-US
     * Request the values of the insight endpoint
     *
     * @param useCache Se pega o último resultado obtido. Resultados são guardados mesmo em chamadas
     *                 com retorno sem sucesso.
     *                 If it takes the last obtained result. Results are saved even if their calls
     *                 return a non success.
     *
     * @exception Exception Qualquer execeção provida de Call.
     *                      Any exception which comes from Call.
     *
     * @return Resposta com a lista de dados do insight.
     *         Response with a list of insight data.
     */
    suspend fun getInsight(useCache: Boolean = false): Response<ArrayList<InsightInfo>>? {
        if (useCache && cache[InsightType.INSIGHT] != null) {
            @Suppress("UNCHECKED_CAST")
            return cache[InsightType.INSIGHT] as Response<ArrayList<InsightInfo>>
        }

        return call(InsightType.INSIGHT)
    }

    /**
     * PT-BR
     * Pega os valores do endpoint do insight porém com o intuito de somente salvar os resultados
     * na mémoria.
     *
     * EN-US
     * Request the values of the insight endpoint but with the intent of only saving it to the
     * memory.
     */
    suspend fun cacheInsight() {
        cacheCall<ArrayList<InsightInfo>>(InsightType.INSIGHT)
    }

    /**
     * PT-BR
     * Retorna um trabalho relacionado a chamada que pode ou já ocorreu durante algum período. Caso
     * não tenha ocorrido nenhuma chamada ainda, é retornado nulo.
     *
     * EN-US
     * Returns if there's a job related to the call which could or have happened in a time period.
     * If no job happened prior to a call, null is returned.
     *
     * @return Job CompletableJob que indica se a chamada finalizou ou não
     *             CompletableJob indicates if a call ended or not.
     */
    fun jobInsight(): Job? = progressCalls[InsightType.INSIGHT]

    companion object {
        var INSTANCE: InsightController? = null

        fun getController(service: InsightService = InsightService.create()): InsightController {
            return INSTANCE ?: synchronized(this) {
                val instance = InsightController(service)
                INSTANCE = instance
                instance
            }
        }
    }

    /**
     * PT-BR
     * Analisa o objeto JSON a fim de obter uma lista de dados do Insight.
     *
     * EN-US
     * Parses the JSON object to get a list of InsightData
     *
     * @author Matheus
     *
     * @return Lista de dados do Insight
     *         List of insight data
     */
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

    /**
     * PT-BR
     * Cria uma nova Response onde nela vamos abranger retornos com o valor de 200 mas que
     */
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


/**
 * PT-BR
 * Difere o endpoint a ser usado para que a API controle as chamadas sendo feitas.
 *
 * EN-US
 * Differs the endpoint to be used so that the API control the calls being done.
 *
 * @author Jomar Júnior
 */
enum class InsightType{
    INSIGHT // Endpoint principal do insight.
}