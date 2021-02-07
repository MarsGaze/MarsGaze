package com.digitalhouse.marsgaze.controllers.service

import kotlinx.coroutines.Job
import retrofit2.Response

/**
 * PT-BR
 * Controla as chamadas para API, garantindo que somente uma esteja sendo executada em qualquer
 * parte do programa. É também armazenado os valores retornados da API no cache para reutilização.
 *
 * EN-US
 * Control the API calls, ensuring that only one request is being made in any part of our software.
 * We do actually save, on the memory, the last value taken from the API.
 *
 * @author Jomar Júnior
 *
 * @property CommonIdentifier Tipo que será usada para identificar as chamadas, usar enum class
 *                         Type which will be used to identify the calls, use enum class
 */
abstract class ServiceController<CommonIdentifier> {
    protected val cache: MutableMap<CommonIdentifier, Any?> = mutableMapOf()

    /**
     * PT-BR
     * Verifica se uma requisição ainda está em processo
     *
     * EN-US
     * Verifies if a request is still on going
     */
    private val onCall: MutableMap<CommonIdentifier, Boolean> = mutableMapOf()

    protected val progressCalls:
            MutableMap<CommonIdentifier, Job> = mutableMapOf()

    protected val calls: MutableMap<CommonIdentifier, suspend () -> Any> = mutableMapOf()

    protected suspend fun<T> call (type: CommonIdentifier): Response<T>? {
        if (onCall[type] == true) {
            return null
        }

        onCall[type] = true

        // Controla se a chamada ainda está acontecendo
        val job = Job()
        progressCalls[type] = job
        // Temos certeza que devemos receber o tipo T correto aqui. Qualquer problema aqui não,
        // deve ser alcançado por usuário
        @Suppress("UNCHECKED_CAST")
        val value: Response<T> = calls[type]!!.invoke() as Response<T>
        cache[type] = value
        job.complete()

        onCall[type] = false

        return value
    }

    protected suspend fun<T> cacheCall(type: CommonIdentifier) {
        if (onCall[type] == true) {
            return
        }

        onCall[type] = true
        val job = Job()
        progressCalls[type] = job
        // Temos certeza que devemos receber o tipo T correto aqui. Qualquer problema aqui não,
        // deve ser alcançado por usuário
        @Suppress("UNCHECKED_CAST")
        val value = calls[type]?.invoke() as Response<T>
        cache[type] = value
        job.complete()

        onCall[type] = false
    }

}