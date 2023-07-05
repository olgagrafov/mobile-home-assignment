package io.tomorrow.basicweather.jsonSerialization

object IJsonSerializerFactory {
    fun create(): IJsonSerializer {
        return JsonSerializer()
    }
}
