package io.tomorrow.basicweather.network

import io.tomorrow.basicweather.jsonSerialization.JsonSerializer
import io.tomorrow.basicweather.network.di.NetworkModule.createIHttpRequestBuilderProvider
import io.tomorrow.basicweather.network.di.NetworkModule.createIHttpResponseValidator
import io.tomorrow.basicweather.network.ktorNetworkCallExecutor.KtorNetworkCallExecutor

object INetworkCallExecutorFactory {
    fun create(): INetworkCallExecutor {
        return KtorNetworkCallExecutor(
            createIHttpRequestBuilderProvider(),
            createIHttpResponseValidator(),
            JsonSerializer()
        )
    }
}
