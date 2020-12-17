package com.digitalhouse.marsgaze.controllers.service

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import java.net.UnknownHostException
import retrofit2.Response as Response

/**
 * PT-BR
 * Controla as chamadas para API, garantindo que somente uma esteja sendo executada em qualquer
 * parte do programa. É também armazenado os valores retornados da API no cache para reutilização.
 *
 * EN-US
 * Control the cache from calls in a non persistent way.
 */
abstract class ServiceController<CommonIdentifier>() {
    protected val cache: MutableMap<CommonIdentifier, Any?> = mutableMapOf()

    protected val onCall: MutableMap<CommonIdentifier, Boolean> = mutableMapOf()

    protected val progressCalls:
            MutableMap<CommonIdentifier, Job> = mutableMapOf()

    protected val calls: MutableMap<CommonIdentifier, suspend () -> Any> = mutableMapOf()

    protected suspend fun<T> call (type: CommonIdentifier): Response<T>? {
        if (onCall[type] == true) {
            return null
        }

        onCall[type] = true

        // Temos certeza que devemos receber o tipo T correto aqui. Qualquer problema aqui não,
        // deve ser alcançado por usuário
        @Suppress("UNCHECKED_CAST")
        val value: Response<T> = calls[type]?.invoke() as Response<T>
        cache[type] = value

        onCall[type] = false

        return value
    }

    protected suspend fun<T> cacheCall(type: CommonIdentifier) {
        if (onCall[type] == true) {
            return
        }

        onCall[type] = true

        // Temos certeza que devemos receber o tipo T correto aqui. Qualquer problema aqui não,
        // deve ser alcançado por usuário
        @Suppress("UNCHECKED_CAST")
        val value = calls[type]?.invoke() as Response<T>
        cache[type] = value

        onCall[type] = false
    }

    suspend fun awaitCall(type: CommonIdentifier) {
        val future = progressCalls[type]
        future?.join()
    }
}