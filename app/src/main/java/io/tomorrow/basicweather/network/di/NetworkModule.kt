package io.tomorrow.basicweather.network.di

import io.tomorrow.basicweather.network.ktorNetworkCallExecutor.HttpMethodProvider
import io.tomorrow.basicweather.network.ktorNetworkCallExecutor.IHttpMethodProvider
import io.tomorrow.basicweather.network.ktorNetworkCallExecutor.HttpRequestBuilderProvider
import io.tomorrow.basicweather.network.ktorNetworkCallExecutor.HttpResponseValidator
import io.tomorrow.basicweather.network.ktorNetworkCallExecutor.IHttpRequestBuilderProvider
import io.tomorrow.basicweather.network.ktorNetworkCallExecutor.IHttpResponseValidator
import io.tomorrow.basicweather.network.ktorNetworkCallExecutor.IUrlProtocolProvider
import io.tomorrow.basicweather.network.ktorNetworkCallExecutor.UrlProtocolProvider

internal object NetworkModule {

    fun createIHttpResponseValidator(): IHttpResponseValidator {
        return HttpResponseValidator()
    }

    fun createIHttpRequestBuilderProvider(): IHttpRequestBuilderProvider {
        return HttpRequestBuilderProvider(
            createIHttpMethodProvider(),
            createIUrlProtocolProvider()
        )
    }

    fun createIHttpMethodProvider(): IHttpMethodProvider {
        return HttpMethodProvider()
    }

    fun createIUrlProtocolProvider(): IUrlProtocolProvider {
        return UrlProtocolProvider()
    }
}
