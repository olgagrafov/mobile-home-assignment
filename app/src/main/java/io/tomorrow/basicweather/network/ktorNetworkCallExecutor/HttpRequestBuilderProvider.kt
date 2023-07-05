package io.tomorrow.basicweather.network.ktorNetworkCallExecutor

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.path
import io.tomorrow.basicweather.network.NetworkCall

internal interface IHttpRequestBuilderProvider {
    fun get(networkCall: NetworkCall): HttpRequestBuilder
}

internal class HttpRequestBuilderProvider(
    private val httpMethodProvider: IHttpMethodProvider,
    private val urlProviderProvider: IUrlProtocolProvider
) : IHttpRequestBuilderProvider {

    override fun get(networkCall: NetworkCall): HttpRequestBuilder {
        return HttpRequestBuilder().apply {
            appendHeaders(networkCall)
            method = httpMethodProvider.get(networkCall)
            url {
                protocol = urlProviderProvider.get(networkCall)
                host = networkCall.hostAbility.host
                path(networkCall.pathAbility.path)
            }
            appendParameters(networkCall)
            appendBody(networkCall)
        }
    }

    private fun HttpRequestBuilder.appendHeaders(networkCall: NetworkCall) {
        networkCall.headerAbilities.forEach { header(it.key, it.value) }
    }

    private fun HttpRequestBuilder.appendParameters(networkCall: NetworkCall) {
        networkCall.parameterAbilities.forEach { parameter(it.key, it.value) }
    }

    private fun HttpRequestBuilder.appendBody(networkCall: NetworkCall) {
        networkCall.jsonBodyAbility?.let {
            contentType(ContentType.Application.Json)
            setBody(it.jsonString)
        }
    }
}
