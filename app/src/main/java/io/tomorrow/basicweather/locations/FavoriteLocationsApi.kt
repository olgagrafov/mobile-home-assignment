package io.tomorrow.basicweather.locations

import io.tomorrow.basicweather.network.NetworkCall
import io.tomorrow.basicweather.network.NetworkCallExecutor
import io.tomorrow.basicweather.network.abilities.HostAbility
import io.tomorrow.basicweather.network.abilities.IHttpMethodAbility
import io.tomorrow.basicweather.network.abilities.ISchemeAbility
import io.tomorrow.basicweather.network.abilities.PathAbility

object FavoriteLocationsApi {

    suspend fun getFavoriteLocations(): FavoriteLocations {
        return NetworkCallExecutor.execute(
            object : NetworkCall() {
                override val hostAbility: HostAbility = HostAbility("olga1-code-interview.free.beeceptor.com")
                override val schemeAbility: ISchemeAbility = ISchemeAbility.Https
                override val httpMethodAbility: IHttpMethodAbility = IHttpMethodAbility.Get
                override val pathAbility: PathAbility = PathAbility("/favorite-locations")
            },
            FavoriteLocations.serializer()
        )
    }
    suspend fun getFavoriteItemLocations(id: String): ItemLocation {
        return NetworkCallExecutor.execute(
            object : NetworkCall() {
                override val hostAbility: HostAbility = HostAbility("olga1-code-interview.free.beeceptor.com")
                override val schemeAbility: ISchemeAbility = ISchemeAbility.Https
                override val httpMethodAbility: IHttpMethodAbility = IHttpMethodAbility.Get
                override val pathAbility: PathAbility = PathAbility("/location/:$id")
            },
            ItemLocation.serializer()
        )
    }
}

