package io.tomorrow.basicweather.network.ktorNetworkCallExecutor

import io.ktor.http.URLProtocol
import io.tomorrow.basicweather.network.NetworkCall
import io.tomorrow.basicweather.network.abilities.ISchemeAbility

internal interface IUrlProtocolProvider {
    fun get(networkCall: NetworkCall): URLProtocol
}

internal class UrlProtocolProvider : IUrlProtocolProvider {
    override fun get(networkCall: NetworkCall): URLProtocol {
        return when (networkCall.schemeAbility) {
            ISchemeAbility.Http -> URLProtocol.HTTP
            ISchemeAbility.Https -> URLProtocol.HTTPS
        }
    }
}
