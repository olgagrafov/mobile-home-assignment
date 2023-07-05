package io.tomorrow.basicweather.network

import io.tomorrow.basicweather.network.abilities.HeaderAbility
import io.tomorrow.basicweather.network.abilities.HostAbility
import io.tomorrow.basicweather.network.abilities.IHttpMethodAbility
import io.tomorrow.basicweather.network.abilities.ISchemeAbility
import io.tomorrow.basicweather.network.abilities.JsonBodyAbility
import io.tomorrow.basicweather.network.abilities.ParameterAbility
import io.tomorrow.basicweather.network.abilities.PathAbility

abstract class NetworkCall {
    abstract val httpMethodAbility: IHttpMethodAbility
    abstract val schemeAbility: ISchemeAbility
    abstract val hostAbility: HostAbility

    open val pathAbility: PathAbility = PathAbility("")
    open val headerAbilities: List<HeaderAbility> = emptyList()
    open val parameterAbilities: List<ParameterAbility> = emptyList()
    open val jsonBodyAbility: JsonBodyAbility? = null
}
