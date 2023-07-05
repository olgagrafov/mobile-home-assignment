package io.tomorrow.basicweather.network.abilities

sealed interface ISchemeAbility : INetworkCallAbility {
    object Http : ISchemeAbility
    object Https : ISchemeAbility
}
