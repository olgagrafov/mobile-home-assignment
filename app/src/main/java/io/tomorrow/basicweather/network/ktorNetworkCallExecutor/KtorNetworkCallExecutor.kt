package io.tomorrow.basicweather.network.ktorNetworkCallExecutor

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import io.tomorrow.basicweather.jsonSerialization.IJsonSerializer
import io.tomorrow.basicweather.network.INetworkCallExecutor
import io.tomorrow.basicweather.network.NetworkCall
import kotlinx.serialization.KSerializer

internal class KtorNetworkCallExecutor(
    private val httpRequestBuilderProvider: IHttpRequestBuilderProvider,
    private val httpResponseValidator: IHttpResponseValidator,
    private val jsonSerializer: IJsonSerializer
) : INetworkCallExecutor {

    private val httpClient by lazy { createKtorHttpClient() }

    override suspend fun <ResponseType> execute(
        networkCall: NetworkCall,
        serializer: KSerializer<ResponseType>
    ): ResponseType {
        val httpResponse = makeRequest(networkCall)
        httpResponse.validate()
        return jsonSerializer.decodeFromStringOrThrow(httpResponse.bodyAsText(), serializer)
    }

    override suspend fun execute(networkCall: NetworkCall) {
        makeRequest(networkCall).validate()
    }

    private suspend fun makeRequest(networkCall: NetworkCall): HttpResponse {
        return httpClient.request(httpRequestBuilderProvider.get(networkCall))
    }

    private fun createKtorHttpClient(): HttpClient {
        return HttpClient {
            install(ContentNegotiation) {
                json(jsonSerializer.getJsonInstance())
            }
        }
    }

    private suspend fun HttpResponse.validate() {
        httpResponseValidator.validateStatusCode(this.status, this.bodyAsText(), headers)
    }
}
