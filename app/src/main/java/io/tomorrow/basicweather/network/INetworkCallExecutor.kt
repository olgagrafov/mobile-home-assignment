package io.tomorrow.basicweather.network

import kotlinx.serialization.KSerializer

interface INetworkCallExecutor {
    suspend fun <ResponseType> execute(networkCall: NetworkCall, serializer: KSerializer<ResponseType>): ResponseType
    suspend fun execute(networkCall: NetworkCall)
}
