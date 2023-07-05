package io.tomorrow.basicweather.network.ktorNetworkCallExecutor

import io.ktor.http.HttpMethod
import io.tomorrow.basicweather.network.NetworkCall
import io.tomorrow.basicweather.network.abilities.IHttpMethodAbility

internal interface IHttpMethodProvider {
    fun get(networkCall: NetworkCall): HttpMethod
}

internal class HttpMethodProvider : IHttpMethodProvider {
    override fun get(networkCall: NetworkCall): HttpMethod {
        return when (networkCall.httpMethodAbility) {
            IHttpMethodAbility.Get -> HttpMethod.Get
            IHttpMethodAbility.Post -> HttpMethod.Post
        }
    }
}
