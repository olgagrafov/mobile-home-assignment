package io.tomorrow.basicweather.network

import kotlinx.serialization.KSerializer

object NetworkCallExecutor : INetworkCallExecutor {

    private val networkCallExecutor by lazy { INetworkCallExecutorFactory.create() }

    override suspend fun <ResponseType> execute(
        networkCall: NetworkCall,
        serializer: KSerializer<ResponseType>
    ): ResponseType {
        return networkCallExecutor.execute(networkCall, serializer)
    }

    override suspend fun execute(networkCall: NetworkCall) {
        networkCallExecutor.execute(networkCall)
    }
}
