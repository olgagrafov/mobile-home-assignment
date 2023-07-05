package io.tomorrow.basicweather.network.abilities

sealed interface IHttpMethodAbility : INetworkCallAbility {
    object Get : IHttpMethodAbility
    object Post : IHttpMethodAbility
}
